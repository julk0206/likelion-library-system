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

}

