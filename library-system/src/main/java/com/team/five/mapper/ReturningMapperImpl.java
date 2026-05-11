
package com.team.five.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.team.five.config.SqlSessionFactoryManager;
import com.team.five.dto.BookItemDto;
import com.team.five.dto.RentDto;
import com.team.five.dto.response.OverdueResponseDto;

public class ReturningMapperImpl implements IReturningMapper {

  private Logger log = LoggerFactory.getLogger(this.getClass());
  private SqlSessionFactory manager = SqlSessionFactoryManager.getFactory();
  private final String NS = "com.team.five.mapper.IReturningMapper.";

  @Override
  public RentDto getUnreturnedRentId(int userId, int bookId) {

    log.info("getUnreturnedRentId\t args\t userId : {}\t bookId : {}", userId, bookId);

    try (SqlSession session = manager.openSession()) {

      Map<String, Object> param = new HashMap<>();

      param.put("userId", userId);
      param.put("bookId", bookId);

      return session.selectOne(NS + "getUnreturnedRentId", param);

    }

  }

  @Override
  public int updateBookAsReturning(int rentId) {

    log.info("updateBookAsReturning\t arg\t rentId : {}", rentId);

    try (SqlSession session = manager.openSession(true)) {

      return session.update(NS + "updateBookAsReturning", rentId);

    }

  }

  @Override
  public RentDto getRentedItemId(int rentId) {

    log.info("getRentedItemId\t arg\t rentId : {}", rentId);

    try (SqlSession session = manager.openSession()) {

      return session.selectOne(NS + "getRentedItemId", rentId);

    }
  }

  @Override
  public BookItemDto getTopPriorityReservation(int bookId) {

    log.info("getTopPriorityReservation\t arg\t bookId : {}", bookId);

    try (SqlSession session = manager.openSession()) {

      return session.selectOne(NS + "getTopPriorityReservation", bookId);

    }
  }

  @Override
  public BookItemDto getRentAvailableItemId(int bookId) {

    log.info("getRentAvailableItemId\t arg\t bookId : {}", bookId);

    try (SqlSession session = manager.openSession()) {

      return session.selectOne(NS + "getRentAvailableItemId", bookId);

    }
  }

  @Override
  public int updateBookStatus(int itemId, String status) {

    log.info("updateBookStatus\t args\t itemId : {}\t status : {}", itemId, status);

    try (SqlSession session = manager.openSession(true)) {

      Map<String, Object> param = new HashMap<>();

      param.put("itemId", itemId);
      param.put("status", status);

      return session.update(NS + "updateBookStatus", param);

    }
  }

  @Override
  public int updateReservationAsRented(int reservationId) {

    log.info("updateReservationAsRented\t arg\t reservationId : {}", reservationId);

    try (SqlSession session = manager.openSession()) {

      return session.update(NS + "updateReservationAsRented", reservationId);

    }

  }

  @Override
  public int insertRentRaw(int userId, int itemId) {

    log.info("insertRentRaw\t args\t userId : {}\t itemId : {}", userId, itemId);

    try (SqlSession session = manager.openSession()) {

      Map<String, Object> param = new HashMap<>();

      param.put("userId", userId);
      param.put("itemId", itemId);

      return session.update(NS + "insertRentRaw", param);

    }
  }

  @Override
  public List<OverdueResponseDto> getOverdueInfo() {

    log.info("getOverdueInfo");

    try (SqlSession session = manager.openSession()) {

      return session.selectList(NS + "getOverdueInfo");
    }
  }
}
