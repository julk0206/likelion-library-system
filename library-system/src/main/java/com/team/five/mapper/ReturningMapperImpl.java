
package com.team.five.mapper;

import java.util.List;
import java.util.Map;

import com.team.five.dto.BookItemDto;
import com.team.five.dto.RentDto;
import com.team.five.dto.ReservationDto;
import com.team.five.dto.response.OverdueResponseDto;

public class ReturningMapperImpl implements IReturningMapper {

  @Override
  public RentDto getRentId(int userId, int bookId) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getRentId'");
  }

  @Override
  public int updateBookAsRetuning(int rentId) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'updateBookAsRetuning'");
  }

  @Override
  public ReservationDto getItemId(int rentId) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getItemId'");
  }

  @Override
  public BookItemDto getTopPriorityReservation(int bookId) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getTopPriorityReservation'");
  }

  @Override
  public int updateBookStatusToggle(int itemId, boolean toggleOption) {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public int updateReservationAsRented(int reservationId) {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public int insertRentRaw(int userId, int itemId) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'insertRentRaw'");
  }

  @Override
  public List<OverdueResponseDto> getOverdueInfo() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getOverdueInfo'");
  }

}
