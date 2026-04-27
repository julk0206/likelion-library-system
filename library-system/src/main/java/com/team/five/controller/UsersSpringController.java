package com.team.five.controller;

import com.team.five.dto.JwtTokenDto;
import com.team.five.dto.LoginDto;
import com.team.five.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/spring/users")
public class UsersSpringController {

    private final UsersService usersService;

    @PostMapping("/login")
    public ResponseEntity<JwtTokenDto> logIn(@RequestBody LoginDto loginDto) {

        // userService.logIn() 에서 반환된 토큰 정보를 jwtTokenDto에 담는다
        JwtTokenDto jwtTokenDto = usersService.logIn(loginDto);

        // 200 ok + jwtToken 정보 반환
        return ResponseEntity.ok(jwtTokenDto);
    }

}
