package com.team.five.mapper;

import com.team.five.dto.UsersDto;

public interface UsersMapper {
    UsersDto selectUsersByUserId(int userId);
}
