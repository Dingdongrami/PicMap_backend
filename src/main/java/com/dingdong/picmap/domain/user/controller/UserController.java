package com.dingdong.picmap.domain.user.controller;

import com.dingdong.picmap.config.jwt.JwtToken;
import com.dingdong.picmap.domain.user.dto.LoginRequest;
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

    @PostMapping("/login")
    public ResponseEntity<JwtToken> login(@RequestBody LoginRequest loginRequest) {
        log.info("login 시도");
        log.info("login email: {}", loginRequest.getEmail());
        return ResponseEntity.ok(userService.login(loginRequest));
    }

}
