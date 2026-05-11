package com.team.five.mapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;

import com.team.five.config.SqlSessionFactoryManager;
import com.team.five.dto.BookItemDto;
import com.team.five.dto.RentDto;
import com.team.five.dto.response.OverdueResponseDto;

public class ReturningServiceTest {

  private IReturningMapper iReturningMapper = new ReturningMapperImpl();

  @Test
  public void databaseConnectionTest() {

    SqlSessionFactory manager = SqlSessionFactoryManager.getFactory();
    SqlSession session = manager.openSession();
    assertNotNull(session);

  }

  @Test
  public void getUnreturnedRentIdImplTest() {
    /*
     * 아래 쿼리로 체크하여 다음 데이터 확인
     *
     * Query>
     * SELECT RENT_ID, USER_ID, b.BOOK_ID, bi.ITEM_ID, TITLE , STATUS
     * FROM RENT r, BOOK_ITEM bi , BOOK b
     * WHERE r.ITEM_ID=bi.ITEM_ID and bi.BOOK_ID = b.BOOK_ID
     *
     */

    int userId = 23;
    int bookId = 8;

    RentDto result = iReturningMapper.getUnreturnedRentId(userId, bookId);

    assertNotNull(result.getRentId());

  }

  @Test
  public void updateBookAsReturningImplTest() {
    /*
     * 확인된 RendId 를 통해 반납(update) Test
     */

    int userId = 23;
    int bookId = 8;

    RentDto targetRentDto = iReturningMapper.getUnreturnedRentId(userId, bookId);

    int resultStatus = iReturningMapper.updateBookAsReturning(targetRentDto.getRentId());

    assertEquals(1, resultStatus);

  }

  @Test
  public void getRentedItemIdImplTest() {
    /*
     * 확인된 RentId 를 통해 ItemId 확인
     */

    int userId = 23;
    int bookId = 8;

    RentDto targetRentDto = iReturningMapper.getUnreturnedRentId(userId, bookId);

    RentDto result = iReturningMapper.getRentedItemId(targetRentDto.getRentId());

    assertEquals(11, result.getItemId());

  }

  @Test
  public void getTopPriorityReservationImplTest() {
    /*
     * 테스트 데이터 추가 후 확인
     * - 테스트 데이터 쿼리
     * INSERT INTO RESERVATION(USER_ID, BOOK_ID) VALUES(4, 8);
     * INSERT INTO RESERVATION(USER_ID, BOOK_ID) VALUES(5, 8);
     */

    int bookId = 8;

    BookItemDto result = iReturningMapper.getTopPriorityReservation(bookId);

    assertNotNull(result);
    assertEquals(8, result.getBookId());

  }

  @Test
  public void getRentAvailableItemIdImplTest() {
    /*
     * 대여 가능한 책의 실제 Item_Id 조회 테스트
     */

    int bookId = 9;

    BookItemDto result = iReturningMapper.getRentAvailableItemId(bookId);

    assertNotNull(result.getItemId());
    assertEquals(13, result.getItemId());

  }

  @Test
  public void updateBookStatusImplTest() {
    /*
     * 단일 책의 대여 가능 여부를 status 로 변경(update)
     */

    int itemId = 11;
    String status = "대여가능";

    int result = iReturningMapper.updateBookStatus(itemId, status);

    assertEquals(1, result);

    /*
     * 업데이트 반영 확인 (autoCommit -> True)
     */

    int bookId = 8;

    BookItemDto availableBookResult = iReturningMapper.getRentAvailableItemId(bookId);

    assertEquals(11, availableBookResult.getItemId());

  }

  @Test
  public void updateReservationAsRentedImplTest() {
    /*
     * 대여로 인한 status 변경 : 예약진행중 -> 대출완료
     */

    int reservationId = 21;

    int result = iReturningMapper.updateReservationAsRented(reservationId);

    assertEquals(1, result);
  }

  @Test
  public void insertRentRawTest() {
    /*
     * 대여(Rent) 데이터 생성 : 기본 반납일(Due_Date 대여일로부터 +7일
     */

    int userId = 4;
    int itemId = 13;

    int result = iReturningMapper.insertRentRaw(userId, itemId);

    // 렌트 성공
    assertEquals(1, result);

    int updateResult = iReturningMapper.updateBookStatus(itemId, "대여중");

    assertEquals(1, updateResult);

  }

  @Test
  public void getOverdueInfoTest() {
    /*
     * 조회일 당시 기한을 넘은 모든 대여중 데이터 조회 : local Test DB 중 한 개 나옴
     */
    List<OverdueResponseDto> result = iReturningMapper.getOverdueInfo();

    assertNotNull(result);

  }
}
