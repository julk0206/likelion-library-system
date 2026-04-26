package com.team.five.jwt;

import com.team.five.dto.JwtTokenDto;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {

    private final SecretKey secretKey;

    // 토큰 만료 기간
    private static final long accessTokenExpiresIn = 3600000;
    private static final long refreshTokenExpiresIn = 604800000;


    // application.properties 에서 secret 값 가져와서 key에 저장
    public JwtTokenProvider(@Value("${jwt.secret}") String secret) {
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }


    // User 정보를 이용해 accessToken, refreshToken 생성하는 method
    public JwtTokenDto generateToken(Authentication authentication) {

        // 객체에서 권한 목록 불러옴
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = (new Date()).getTime();

        // accessToken 생성
        String accessToken = Jwts.builder()
                .subject(authentication.getName())
                .claim("auth", authorities)
                .expiration(new Date(now + accessTokenExpiresIn))
                .signWith(secretKey)
                .compact();

        // refreshToken 생성
        String refreshToken = Jwts.builder()
                .expiration(new Date(now + refreshTokenExpiresIn))
                .signWith(secretKey)
                .compact();

        return new JwtTokenDto("Bearer", accessToken, refreshToken);
    }


    // 토큰에서 인증 정보를 꺼내는 method
    public Authentication getAuthentication(String accessToken) {

        /*
            Payload : 토큰의 실제 데이터가 담겨져 있는 곳, Body라고 생각하면 편함
            Claims : Payload 안에 담겨져있는 데이터 조각
        */
        Claims claims = parseClaims(accessToken);

        if (claims.get("auth") == null) {
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }

        // claims에서 인증 정보 가져와서 authorites에 넣기
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get("auth").toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        // User는 UserDetails(interface)를 구현해둔 객체(구현체)
        // UsernamePasswordAuthenticationToken : 인증 수행을 위해 인증 정보를 담는 객체
        UserDetails principal = new User(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    // 토큰 유효성 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    // 암호화된 토큰 문자열을 데이터 객체로 파싱
    private Claims parseClaims(String accessToken) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(accessToken)
                .getPayload();
    }

}
