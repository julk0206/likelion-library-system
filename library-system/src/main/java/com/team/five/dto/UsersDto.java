package com.team.five.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UsersDto implements UserDetails {
    /*
        UserDeatils : Spring-Security에서 제공되는 사용자 정보 담는 인터페이스
    */

    private int userId;
    private String name;
    private int age;
    private String password;
    private String joinDate;

    
    // 이 아래로 UsersDetails 상속받아서 구현해야 하는 method들

    /*
        권한 목록 반환하는 method
        
        SimpleGrantedAuthority란? 사용자에게 부여된 권한을 문자열로 표현해주는 클래스
        
        반환값을 Collections.singletonList(new SimpleGrantedAuthority(...)); 으로 쓸수도 있다고함, 차이점 : Collections는 null 허용, List.of는 null X
    */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER")); 
    }

    @Override
    public String getUsername() {
        return String.valueOf(userId);
    }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }

}
