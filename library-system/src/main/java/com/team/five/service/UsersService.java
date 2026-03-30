package com.team.five.service;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.team.five.config.SqlSessionFactoryManager;
import com.team.five.dto.UsersDto;
import com.team.five.mapper.UsersMapper;

public class UsersService {
    private final SqlSessionFactory manager;

    public UsersService() {
        this.manager = SqlSessionFactoryManager.getFactory();
    }

    // USER_ID 기준 사용자 조회
    public UsersDto getUserByUserId(int userId) {
        try (SqlSession session = manager.openSession()) {
            UsersMapper mapper = session.getMapper(UsersMapper.class);

            return mapper.selectUsersByUserId(userId);
        }
    }


    // 사용자 추가
    public int insertUser(UsersDto usersDto){
        try(SqlSession session = manager.openSession(true)){
            int result = 0;
            UsersMapper mapper = session.getMapper(UsersMapper.class);
            result = mapper.insertUser(usersDto);
            return result;
        }
    }
}
