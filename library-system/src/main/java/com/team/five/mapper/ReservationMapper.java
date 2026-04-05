package com.team.five.mapper;

import com.team.five.dto.ReservationDto;

import java.util.List;

public interface ReservationMapper {
    // 1. 예약 등록
    int insertReservation(ReservationDto reservation);
    // 2-1. 예약 조회 (사용자별)
    List<ReservationDto> selectReservationsByUserId(int userId);
    // 2-2. 예약 조회 (책별 -> 예약 우선순위 조회 )
    List<ReservationDto> selectReservationsByBookId(int bookId);
    // 3. 예약 취소
    int deleteReservation(int reservationId);
    // 4. 예약 존재 여부 확인 (책별)
    boolean existsReservationByBookId(int bookId);
    // 5. 예약자 수 확인
    int countReservationsByBookId(int bookId);
    // 6. 예약 존재 여부 확인 (사용자별)
    boolean existsReservationByUserId(int userId);
    // 7. 중복 예약 확인 (등록 전)
    boolean existsReservationByUserIdBookId(ReservationDto reservation);
}