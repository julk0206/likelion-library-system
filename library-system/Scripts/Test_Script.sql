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

-- 반납 처리 (RENT.RETURN_DATE 업데이트)
-- RENT.STATUS 업데이트 (반납 완료/연체 여부 판단)
-- BOOK ITEM.STATUS '대여가능' 변경
-- 반납된 BOOK ID 기준 예약 목록 조회
-- 예약 우선순위 판단 (RESERVATION_DATE 오름차순)
-- 우선순위 1순위 예약에 대해 자동 RENT 생성
--BOOK_ITEM.STATUS -'대여중' 변경 (자동 대출)
-- RESERVATION.STATUS - '대출완료' 변경
-- 연체 도서 조회 (DUE_DATE 초과 미반납)




SELECT * FROM RENT;

-- 입력 : name, book_id

-- 1. 반납처리
UPDATE RENT
   SET RETURN_DATE = sysdate;


-- 3. 책 ID로 대여 toggle;
-- 대여중 -> 대여 가능
UPDATE RENT
   SET STATUS = '대여가능'
 WHERE STATUS = '대여중' AND BOOK_ID = #{BOOK_ID};
-- 대여 가능 -> 대여중
UPDATE RENT
   SET STATUS = '대여중'
 WHERE STATUS = '대여가능' AND BOOK_ID = #{BOOK_ID};


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
    RESERVATION_ID   NUMBER     GENERATED ALWAYS AS IDENTITY,
    USER_ID          NUMBER     NOT NULL,
    BOOK_ID          NUMBER     NOT NULL,
    RESERVATION_DATE DATE       DEFAULT SYSDATE NOT NULL,
    STATUS           VARCHAR2(20) DEFAULT '예약진행중' NOT NULL,
    CONSTRAINT PK_RESERVATION PRIMARY KEY (RESERVATION_ID),
    CONSTRAINT FK_RESERV_USER FOREIGN KEY (USER_ID)
        REFERENCES USERS (USER_ID),
    CONSTRAINT FK_RESERV_BOOK FOREIGN KEY (BOOK_ID)
        REFERENCES BOOK (BOOK_ID),
    CONSTRAINT CHK_RESERVATION_STATUS CHECK (STATUS IN ('예약진행중', '대출완료'))
);


-- 5번(예약관리 및 조회 API) : 특정 Book_id 의 예약 존재 여부
 SELECT CASE WHEN COUNT(*) > 0 THEN 1 ELSE 0 END
            FROM RESERVATION
            WHERE BOOK_ID = #{bookId}
            AND STATUS = '예약진행중'

---------------------------------------------------------------------


