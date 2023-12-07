package com.dingdong.picmap.domain.user.controller;

import com.dingdong.picmap.config.jwt.JwtToken;
import com.dingdong.picmap.domain.user.dto.request.LoginRequestDto;
import com.dingdong.picmap.domain.user.dto.request.SignupRequestDto;
import com.dingdong.picmap.domain.user.dto.request.UserUpdateRequestDto;
import com.dingdong.picmap.domain.user.dto.response.UserProfileUpdateResponseDto;
import com.dingdong.picmap.domain.user.dto.response.UserResponseDto;
import com.dingdong.picmap.domain.user.dto.response.UserUpdateResponseDto;
import com.dingdong.picmap.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.getUser(userId));
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserResponseDto>> getAllUser() {
        return ResponseEntity.ok(userService.getAllUser());
    }

    // 사용자 정보 수정
    @PutMapping("/{userId}")
    public ResponseEntity<UserUpdateResponseDto> update(@PathVariable Long userId, @RequestBody UserUpdateRequestDto userUpdateRequestDto) {
        return ResponseEntity.ok(userService.update(userId, userUpdateRequestDto));
    }

    // 프로필 이미지 수정
    @PutMapping("/{userId}/profile")
    public ResponseEntity<UserProfileUpdateResponseDto> updateProfile(@PathVariable Long userId, @RequestPart(name = "profile") MultipartFile profileImage) {
        return ResponseEntity.ok(userService.updateProfile(userId, profileImage));
    }

    // 기본 프로필 이미지 설정
    @PutMapping("/{userId}/no-profile")
    public ResponseEntity<UserProfileUpdateResponseDto> updateNoProfile(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.updateNoProfile(userId));
    }

}
