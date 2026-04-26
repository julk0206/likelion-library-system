package com.team.five.service;

import com.team.five.dto.JwtTokenDto;
import com.team.five.dto.LoginDto;
import com.team.five.dto.UsersDto;
import com.team.five.jwt.JwtTokenProvider;
import com.team.five.mapper.UsersMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsersService {

    private final UsersMapper usersMapper;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    // USER_ID 기준 사용자 조회
    public UsersDto getUserByUserId(int userId) {
        return usersMapper.selectUsersByUserId(userId);
    }


    // 사용자 추가
    public int insertUser(UsersDto usersDto) {
        return usersMapper.insertUser(usersDto);
    }


    // 로그인 - 인증 후 JWT 토큰 반환
    public JwtTokenDto logIn(LoginDto loginDto) {
        // userId(int) → String 변환 후 인증 객체 생성
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(
                        String.valueOf(loginDto.getUserId()),
                        loginDto.getPassword()
                );

        // AuthenticationManager가 CustomUserDetailsService를 호출해 인증 처리
        // AuthenticationManager : spring security에서 인증 과정을 담당하는 interface
        // authenticate() : 성공 시 인증 정보(Authentication 객체) 반환, 실패 시 예외 throw
        Authentication authentication = authenticationManager.authenticate(authToken);

        return jwtTokenProvider.generateToken(authentication);
    }

}
