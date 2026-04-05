package com.team.five.mapper;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.team.five.dto.ReservationDto;
import com.team.five.service.ReservationService;

public class ReservationServiceTest {
    private ReservationService reservationService;

    @Before
    public void createReservationService(){
        reservationService = new ReservationService();
    }

    @Test
    public void insertReservationTest(){
        reservationService.insertReservation(1, 1);
        int result = reservationService.insertReservation(1, 1);
        assertEquals(1, result);

    }

    @Test
    public void getReservationsByUserIdTest(){
        reservationService.insertReservation(1, 1);
        List<ReservationDto> result = reservationService.getReservationsByUserId(1);
        assertTrue(result.size() >= 1);
    }

    @Test
    public void getReservationsByBookIdTest(){
        List<ReservationDto> result = reservationService.getReservationsByBookId(1);
        assertTrue(result.size() >= 1);
    }

    @Test
    public void deleteReservationTest(){
        int result = reservationService.deleteReservation(1);
        assertEquals(1, result);
    }

    @Test
    public void hasReservationByBookIdTest(){
        reservationService.insertReservation(1, 1);
        boolean result = reservationService.hasReservationByBookId(1);
        assertTrue(result);
    }

    @Test
    public void countReservationsByBookIdTest(){
        reservationService.insertReservation(1, 1);
        int result = reservationService.countReservationsByBookId(1);
        assertTrue(result >= 1);
    }

    @Test
    public void hasReservationByUserIdTest(){
        reservationService.insertReservation(1, 1);
        boolean result = reservationService.hasReservationByUserId(1);
        assertTrue(result);
    }

    @Test
    public void hasReservationByUserIdBookId(){
        reservationService.insertReservation(1, 1);
        boolean result = reservationService.hasReservationByUserIdBookId(1, 1);
        assertTrue(result);
    }
}

