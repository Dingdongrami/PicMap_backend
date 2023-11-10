package com.dingdong.picmap.config.auth;

import com.dingdong.picmap.domain.user.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
/*
  * Spring Security 에서 사용자의 정보를 담는 인터페이스
  * POST /login 요청을 security 가 대신 진행해줌
  * login 성공하면 Security Session 생성해줌
  * Security Session 안에 Authentication 객체가 들어있음
 */
public class PrincipalDetails implements UserDetails {

    private User user;

    public PrincipalDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collections = new ArrayList<>();
        collections.add(() -> user.getRole().toString());
        return collections;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getNickname();
    }

    // 계정 만료 여부 (true: 만료 안됨)
    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    // 계정 잠김 여부 (true: 잠기지 않음)
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 비밀번호 만료 여부 (true: 만료 안됨)
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 계정 활성화 여부 (true: 활성화)
    @Override
    public boolean isEnabled() {
        return true;
    }
}
