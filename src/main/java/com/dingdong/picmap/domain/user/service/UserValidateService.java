package com.dingdong.picmap.domain.user.service;

import com.dingdong.picmap.domain.user.entity.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserValidateService {

    private final PasswordEncoder passwordEncoder;

    public UserValidateService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public void validatePassword(String requestPassword, User user) {
        if(!passwordEncoder.matches(requestPassword, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }
}
