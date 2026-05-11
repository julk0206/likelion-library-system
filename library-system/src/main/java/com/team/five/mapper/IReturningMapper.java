package com.team.five.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.team.five.dto.BookItemDto;
import com.team.five.dto.RentDto;
import com.team.five.dto.response.OverdueResponseDto;

public interface IReturningMapper {

  /**
   * userId 와 bookId 를 통해 현재 "대여중" 인 Rent_Id 를 반환합니다.
   *
   * @param userId -> int
   * @param bookId -> int
   *
   * @return RentDto {rentId -> int}
   */
  public RentDto getUnreturnedRentId(@Param("userId") int userId, @Param("bookId") int bookId);

  /**
   * RENT 테이블의 returnDate를 'sysdate' 로 Update 합니다.
   *
   * @param rentId -> int
   *
   * @return 0 or 1
   */
  public int updateBookAsReturning(int rentId);

  /**
   * rentId를 통해 현재 대여 중인 책에 대한 예약이 있는지 확인
   * Reservation 테이블에서 itemId를 조회
   *
   * @param rentId -> int
   *
   * @return RentDto { itemId -> int}
   */
  public RentDto getRentedItemId(int rentId);

  /**
   * 책 고유 id를 통해 해당 책의 상태를 status 값으로 변경 (Update)
   * 
   * ex) updateBookStatus(123, "대여중");
   *
   *
   * @param itemId -> int
   * @param status -> String
   *
   * @return : 0 or 1
   */
  public int updateBookStatus(@Param("itemId") int itemId, @Param("status") String status);

  /**
   * 수
   * BookId를 통해 해당 책의 가장 우선순위가 높은 사람의 userId와 해당 bookId를 가져옵니다.
   *
   * @param bookId -> int
   *
   * @return : ReservataionDto {userId , bookId}
   */
  public BookItemDto getTopPriorityReservation(int bookId);

  /**
   * bookId 를 통해 대여 가능한 고유 책 ID(itemId) 를 가져 옵니다.
   *
   * @param bookId
   *
   * @return : BookItemDto {itemId}
   */
  public BookItemDto getRentAvailableItemId(int bookId);

  /**
   * 새로운 대여 정보를 생성합니다. Due_date는 기본적으로 +7 일 입니다.
   *
   * @param userId -> int
   * @param itemId -> int
   *
   * @return : 0 or 1
   */
  public int insertRentRaw(@Param("userId") int userId, @Param("itemId") int itemId);

  /**
   * Reservataion 테이블의 예약 상태를 '예약진행중' -> '대출완료'
   *
   * @param reservationId -> int
   *
   * @return : 0 or 1
   */
  public int updateReservationAsRented(int reservationId);

  /**
   * 연체 정보를 모두 불러 옵니다.
   *
   * @return : List<{@link OverdueResponseDto}
   */
  public List<OverdueResponseDto> getOverdueInfo();
}
