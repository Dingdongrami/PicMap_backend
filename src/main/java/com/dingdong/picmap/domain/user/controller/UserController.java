package com.dingdong.picmap.domain.user.controller;

import com.dingdong.picmap.config.jwt.JwtToken;
import com.dingdong.picmap.domain.user.dto.LoginRequestDto;
import com.dingdong.picmap.domain.user.dto.SignupRequestDto;
import com.dingdong.picmap.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<Long> signup(@RequestBody SignupRequestDto signupRequestDto) {
        return ResponseEntity.ok(userService.signup(signupRequestDto));
    }

    @PostMapping("/login")
    public ResponseEntity<JwtToken> login(@RequestBody LoginRequestDto loginRequestDto) {
        return ResponseEntity.ok(userService.login(loginRequestDto));
    }

}
