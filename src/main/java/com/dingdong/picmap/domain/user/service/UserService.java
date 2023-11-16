package com.dingdong.picmap.domain.user.service;

import com.dingdong.picmap.config.jwt.JwtToken;
import com.dingdong.picmap.config.jwt.JwtTokenProvider;
import com.dingdong.picmap.config.util.SecurityUtils;
import com.dingdong.picmap.domain.user.dto.LoginRequest;
import com.dingdong.picmap.domain.user.entity.User;
import com.dingdong.picmap.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final SecurityUtils securityUtils;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final UserRepository userRepository;
    private final UserValidateService userValidateService;

    public JwtToken login(LoginRequest loginRequest) {

        final String requestEmail = loginRequest.getEmail();
        final String requestPassword = loginRequest.getPassword();

        log.info("login email, password: {}, {}", requestEmail, requestPassword);

        User user = userRepository.findByEmail(requestEmail)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        userValidateService.validatePassword(requestPassword, user);
        Authentication authentication = securityUtils.setAuthentication(requestEmail, requestPassword);
        log.info("===> jwt token 생성 finish");
        return jwtTokenProvider.generateToken(authentication);
    }
}
