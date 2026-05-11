/* 
분석 내용
- 들어 간 테이블 : genre, users, book, book_item
- 만져도 되는 테이블 : Reservation , Rent
- RENT 테이블의 STATUS 값 종류 [대여가능, 대여중]
*/

SELECT * FROM GENRE;

SELECT * FROM BOOK_ITEM;

/* ------------------------------------------ */
-- 반납 및 전체 흐름 제어

-- 사용 테이블 : RENT, BOOK_ITEM, RESERVATION -

-- [Action_10 - 회원의 책 반납]
-- [x] 1.반납 처리 (RENT.RETURN_DATE 업데이트)
-- [ ] 2.RENT.STATUS(x)  업데이트 (반납 완료/연체 여부 판단) -> RENT_STATUS 추가 여부 확인
-- [x] 3.BOOK_ITEM.STATUS '대여가능' 변경

-- [Feature_11 - 예약 존재]
-- [x] 1.반납된 BOOK ID 기준 예약 목록 조회
-- [x] 2.예약 우선순위 판단 (RESERVATION_DATE 오름차순)
-- [x] 3.우선순위 1순위 예약에 대해 자동 RENT 생성
-- [x] 4.BOOK_ITEM.STATUS -'대여중' 변경 (자동 대출)
-- [x] 5.RESERVATION.STATUS - '대출완료' 변경

-- [Action_2 - 연체 도서 조회]
-- [x]연체 도서 조회 (DUE_DATE 초과 미반납)

-- 정리 Action => 유저 책 반납시
-- 해당 Book_id 에 대해 예약이 있으면? => 다음 사람에게 자동 대출(BOOK_ITEM.status 가 '대여가능' -> '대여중')
-- 해당 BooK_id 에 대해 예약이 없으면? => 반납 처리(update RENT status, return_date)

SELECT * FROM RENT;

/* -------------------Action_10--------------- */
-- 입력 : userId, bookId
-- # issue1 : 입력을 [name, title] 로 생각하면 RENT에서 RENT_ID UNIQUE 식별이 안됨
--          - 최소조건 : userId 또는 itemId 둘 중 하나라도 있어야 함
--          - 예를 들어) 같은 이름을 가진 사람이 같은 제목의 책을 빌린 상태에서 반납
-- 1.반납 처리
-- 1-1 userID와 bookId 를 통해 RENT_ID 확인
-- Mapped Method Name : getUnreturnedRentId
SELECT RENT_ID
  FROM RENT r 
  JOIN BOOK_ITEM bi ON r.ITEM_ID = bi.ITEM_ID
 WHERE bi.STATUS = '대여중' 
   AND r.USER_ID = #{userId}
   AND bi.BOOK_ID = #{bookId};-- RESULT : RENT_ID_RESULT
-- 1-2 해당 REND_ID 를 통해 RENT 테이블 업데이트
-- Mapped Method Name : updateBookAsReturning
UPDATE RENT
   SET RETURN_DATE = sysdate
 WHERE RENT_ID = v$RENT_ID_RESULT;

-- 3.BOOK_ITEM.STATUS 대여 가능 변경
-- 3-1 RENT_ID 를 통해 다시 ITEM_ID 확인
-- Mapped Method Name : getRentedItemId
SELECT ITEM_ID FROM RENT WHERE RENT_ID = v$RENT_ID_RESULT;
-- REULST : ITEM_ID_RESULT

-- 3-2 ITEM_ID를 통해 BOOK_ITEM 업데이트
-- Mapped Method Name : updateBookStatus
UPDATE BOOK_ITEM
SET
    STATUS = '대여가능'
WHERE
    STATUS = '대여중'
    AND ITEM_ID = v$ITEM_ID_RESULT;

/* -------------------Feature_11--------------- */
-- 1. 2, 반납된 BOOK_ID 기준 예약 목록 조회 -> 수연님 구현 mapper id =selectReservationsByBookId
SELECT USER_ID , BOOK_ID, RESERVATION_ID
  FROM (SELECT USER_ID , BOOK_ID , RESERVATION_ID
             , ROW_NUMBER() OVER(PARTITION BY BOOK_ID ORDER BY SYSDATE ASC) RN
          FROM RESERVATION
         WHERE BOOK_ID = #{bookId}
           AND STATUS = '예약진행중')
 WHERE RN = 1;
-- RESULT : RESERVATION_ID_RESULT , USER_ID_RESULT , BOOK_ID_RESULT

-- 위를 FETCH FIRST 1 ROW ONLY 로 수정
-- Mapped Method Name : getTopPriorityReservation
SELECT USER_ID, BOOK_ID, RESERVATION_ID
  FROM RESERVATION
 WHERE BOOK_ID = #{bookId}
   AND STATUS = '예약진행중'
 ORDER BY RESERVATION_DATE ASC
 FETCH FIRST 1 ROW ONLY;

-- 3.우선순위 1순위 예약에 대해 자동 RENT 생성
-- 3-1 BOOK_ID_RESULT 를 통해 ITEM_ID_RESULT 생성 -> Action_10_3-1 결과 : v$ITEM_ID_RESULT 와 동일해야한다
-- Mapped Method Name : getRentAvailableItemId
SELECT ITEM_ID
FROM BOOK_ITEM
WHERE
    STATUS = '대여가능'
    AND BOOK_ID = v$BOOK_ID_RESULT
    AND ROWNUM = 1;
-- RESULT : v$ITEM_ID_RESULT_2
-- 3-2. v$USER_ID_RESULT , v$ITEM_ID_RESULT_2 를 통해 RENT 생성
-- DUE_DATE 는 1주일로 한다.
-- Mapped Method Name : inserRentRaw
INSERT INTO
    RENT (USER_ID, ITEM_ID, DUE_DATE)
VALUES (
        v$USER_ID_RESULT,
        v$ITEM_ID_RESULT_2,
        SYSDATE + 7
    );

-- 4. BOOK_ITEM.STATUS 업데이트
UPDATE BOOK_ITEM
SET
    STATUS = '대여중'
WHERE
    STATUS = '대여가능'
    AND v$ITEM_ID_RESULT_2;

-- 5.RESERVATION.STATUS - '대출완료' 변경
UPDATE RESERVATION
SET
    STATUS = '대출완료'
WHERE
    STATUS = '예약진행중'
    AND RESERVATION_ID = v$RESERVATION_ID_RESULT;

/* -------------------Action_20--------------- */
-- input : 없음
-- output : TITLE, ITEM_ID , 연체일, NAME , USER_ID
-- Mapped Method Name : getOverdueInfo
SELECT b.TITLE, bi.ITEM_ID, u.NAME, u.USER_ID, TRUNC(SYSDATE - r.DUE_DATE) DELAY_DAYS
FROM
    RENT r
    JOIN BOOK_ITEM bi ON r.ITEM_ID = bi.ITEM_ID
    JOIN BOOK b ON b.BOOK_ID = bi.BOOK_ID
    JOIN USERS u ON r.USER_ID = u.USER_ID
WHERE
    SYSDATE > r.DUE_DATE
    AND bi.STATUS = '대여중';
/* ------------------------------------------ */
-- 5번
-- 예약 관리 및 조회 API

-- 사용 테이블 : RESERVATION
-- 예약 등록 (RESERVATION INSERT, STATUS ='예약진행중)
-- 내 예약 목록 조회 (USER_ID 기준)
-- 예약 취소 (RESERVATION DELETE)
-- 특정 BOOK ID의 예약 존재 여부 반환
-- 특정 BOOK_ID의 예약 목록 반환 (RESERVATION_DATE 오름차순)
-- 특정 USER_ID의 예약 존재 여부 반환
--
-- RESERVATION.STATUS 변경 금지 (4번 전담)

---------------------------------------------------------------------
/*                      EXTRA                                      */
---------------------------------------------------------------------
/*
reservation 테이블에
status 컬럼을 추가하는게 좋을듯 싶은데
의견을 여쭙고 싶어서요

-> 조인 하려고 했던 것 같은데, foreign key를 안 건 이유는?
딱히 반 정규화를 하려던 건 아닌 거 같음

-> 도메인(값 범위)는 RENT 를 따르나??

-> 으아악 00:20 분 . 머리가 굳어 그만두다...


=> Book_item status 컬럼과 다름
=> 도메인범위 (예약진행중, 대출완료)

*/

CREATE TABLE RESERVATION (
    RESERVATION_ID NUMBER GENERATED ALWAYS AS IDENTITY,
    USER_ID NUMBER NOT NULL,
    BOOK_ID NUMBER NOT NULL,
    RESERVATION_DATE DATE DEFAULT SYSDATE NOT NULL,
    STATUS VARCHAR2(20) DEFAULT '예약진행중' NOT NULL,
    CONSTRAINT PK_RESERVATION PRIMARY KEY (RESERVATION_ID),
    CONSTRAINT FK_RESERV_USER FOREIGN KEY (USER_ID) REFERENCES USERS (USER_ID),
    CONSTRAINT FK_RESERV_BOOK FOREIGN KEY (BOOK_ID) REFERENCES BOOK (BOOK_ID),
    CONSTRAINT CHK_RESERVATION_STATUS CHECK (STATUS IN ('예약진행중', '대출완료'))
);

-- 5번(예약관리 및 조회 API) : 특정 Book_id 의 예약 존재 여부
SELECT CASE WHEN COUNT(*) > 0 THEN 1 ELSE 0 END
            FROM RESERVATION
            WHERE BOOK_ID = #{bookId}
            AND STATUS = '예약진행중'

---------------------------------------------------------------------