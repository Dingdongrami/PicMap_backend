package com.dingdong.picmap.domain.user.service;

import com.dingdong.picmap.config.jwt.JwtToken;
import com.dingdong.picmap.config.jwt.JwtTokenProvider;
import com.dingdong.picmap.config.util.SecurityUtils;
import com.dingdong.picmap.domain.user.dto.LoginRequestDto;
import com.dingdong.picmap.domain.user.dto.SignupRequestDto;
import com.dingdong.picmap.domain.user.entity.User;
import com.dingdong.picmap.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final SecurityUtils securityUtils;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final UserValidateService userValidateService;

    public Long signup(SignupRequestDto signupRequestDto) {
        User user = User.builder()
                .email(signupRequestDto.getEmail())
                .password(passwordEncoder.encode(signupRequestDto.getPassword()))
                .nickname(signupRequestDto.getNickname())
                .roles(Collections.singletonList("ROLE_USER"))
                .build();
        return userRepository.save(user).getId();
    }

    public JwtToken login(LoginRequestDto loginRequestDto) {

        final String requestEmail = loginRequestDto.getEmail();
        final String requestPassword = loginRequestDto.getPassword();

        User user = userRepository.findByEmail(requestEmail)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        userValidateService.validatePassword(requestPassword, user);
        Authentication authentication = securityUtils.setAuthentication(requestEmail, requestPassword);
        return jwtTokenProvider.generateToken(authentication);
    }

    // getUser
    public User getUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다. id=" + userId));
    }
}
