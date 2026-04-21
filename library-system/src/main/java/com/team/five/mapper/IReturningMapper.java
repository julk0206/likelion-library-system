package com.team.five.mapper;

import org.apache.ibatis.annotations.Param;

import com.team.five.dto.BookItemDto;
import com.team.five.dto.RentDto;
import com.team.five.dto.ReservationDto;

public interface IReturningMapper {

  /**
   * userId 와 bookId 를 통해 현재 대여중인 Rent_Id 를 반환합니다.
   *
   * @param userId -> int
   * @param bookId -> int
   *
   * @return RentDto {rentId -> int}
   */
  public RentDto getRentId(@Param("userId") int userId, @Param("bookId") int bookId);

  /**
   * RENT 테이블의 returnDate를 'sysdate' 로 Update 합니다.
   *
   * @param rentId -> int
   *
   * @return 0 or 1
   */
  public int updateBookAsRetuning(@Param("rentId") int rentId);

  /**
   * rentId를 통해 현재 대여 중인 책에 대한 예약이 있는지 확인
   * Reservation 테이블에서 itemId를 조회
   *
   * @param rentId -> int
   *
   * @return ReservationDto { itemId -> int}
   */
  public ReservationDto getItemId(@Param("rentId") int rentId);

  /**
   * 책 고유 id를 통해 해당 책의 상태를 변경 (Update)
   *
   * toggleOption ? 대여중 -> 대여가능 : 대여가능 -> 대여중
   *
   *
   * @param itemId       -> int
   * @param toggleOption -> boolean
   *
   * @return : 0 or 1
   */
  public int updateBookStatusToggle(@Param("itemId") int itemId, boolean toggleOption);

  /**
   * BookId를 통해 해당 책의 가장 우선순위가 높은 사람의 userId와 해당 bookId를 가져옵니다.
   *
   * @param bookId -> int
   *
   * @return : ReservataionDto {userId , bookId}
   */
  public BookItemDto getTopPriorityReservation(@Param("bookId") int bookId);

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
  public int updateReservationAsRented(@Param("reservationId") int reservationId);

}
