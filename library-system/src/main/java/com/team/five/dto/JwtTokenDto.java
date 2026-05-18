package com.team.five.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JwtTokenDto {

    // jwt에 대한 인증 타입, Access Token을 HTTP 요청의 Authorization 헤더에 포함하여 전송한다.
    // ex) Authorization: Bearer <access_token> 
    private String grantType;
    
    private String accessToken;
    private String refreshToken;

}
