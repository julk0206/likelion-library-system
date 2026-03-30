package com.team.five.mapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import com.team.five.dto.UsersDto;
import com.team.five.service.UsersService;

public class UsersServiceTest {
    private UsersService usersService;

    @Before
    public void createUsersService(){
        usersService = new UsersService();
    }

    @Test
    public void selectUsersByUserIdTest(){
        UsersDto result = usersService.getUserByUserId(1);
        assertNotNull("조회결과 없음",result);
    }

    @Test
    public void insertUsersTest(){
        UsersDto usersDto = new UsersDto(0, "이진환", 27, null);
        int cnt = usersService.insertUser(usersDto);
        assertEquals(1, cnt);
    }
}
