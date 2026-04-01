package com.team.five.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.team.five.config.SqlSessionFactoryManager;
import com.team.five.dto.ReservationDto;
import com.team.five.mapper.ReservationMapper;

public class ReservationService {
    private final SqlSessionFactory manager;

    public ReservationService() {
        this.manager = SqlSessionFactoryManager.getFactory();
    }

    // 1. 예약 등록
    public int insertReservation(int userId, int bookId) {
        try (SqlSession session = manager.openSession(true)){
            ReservationMapper mapper = session.getMapper(ReservationMapper.class);
            ReservationDto dto = new ReservationDto();
            dto.setUserId(userId);
            dto.setBookId(bookId);
            return mapper.insertReservation(dto);
        }
    }

    // 2-1. 예약 조회 (사용자별)
    public List<ReservationDto> getReservationsByUserId(int userId){
        try(SqlSession session = manager.openSession()){
            ReservationMapper mapper = session.getMapper(ReservationMapper.class);
            return mapper.selectReservationsByUserId(userId);
        }
    }

    // 2-2. 예약 조회 (책별)
    public List<ReservationDto> getReservationsByBookId(int bookId){
        try(SqlSession session = manager.openSession()){
            ReservationMapper mapper = session.getMapper(ReservationMapper.class);
            return mapper.selectReservationsByBookId(bookId);
        }
    }

    // 3. 예약 취소
    public int deleteReservation(int reservationId){
        try(SqlSession session = manager.openSession()){
            ReservationMapper mapper = session.getMapper(ReservationMapper.class);
            return mapper.deleteReservation(reservationId);
        }
    }
    
}
