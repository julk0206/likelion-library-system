package com.team.five.config;

import com.team.five.jwt.JwtAuthenticationFilter;
import com.team.five.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            // 로그인 기능 따로 구현 시 httpBasic 인증 비활성화 해야함
            .httpBasic(httpBasic -> httpBasic.disable())

            // csrf : 세션 쿠키에 악성 요청을 담아 보내는 공격방식
            // jwt는 Authorization header로 전송하기 때문에 csrf 보안을 사용하지 않는다
            .csrf(csrf -> csrf.disable())

            // jwt 사용하기 때문에 session 사용 X
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

            // 접근 권한 설정, 로그인 페이지는 권한 상관없이 모두 허가
            // 추후에 새로운 url 추가 시 .requestMatchers(요청 주소).hasRole("USER") 또는 .requestMatchers(요청 주소).hasAuthority("ROLE_USER") 작성해주면 됨
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/users/login").permitAll()
                .requestMatchers("/users/login.do").permitAll()
                .requestMatchers("/users/register.do").permitAll()
                .requestMatchers("/spring/users/login").permitAll()
                .requestMatchers("/error").permitAll()
                .anyRequest().authenticated())

            // JwtAuthenticationFilter를 UsernamePasswordAuthenticationFilter 전에 실행
            // JwtAuthenticationFilter에서 인증 유효성 확인되면 SecurityContext에 인증 정보 담아서 다음 필터에서 추가 인증 수행 안해도 됨
            .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider),
                UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @SuppressWarnings("deprecation")
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

}
