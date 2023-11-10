//package com.dingdong.picmap.domain.user.controller;
//
//import com.dingdong.picmap.domain.user.dto.LoginRequest;
//import com.dingdong.picmap.domain.user.service.UserService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//@Controller
//@RequiredArgsConstructor
//@RequestMapping("/security-login")
//public class SecurityLoginController {
//
//    private final UserService userService;
//
//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
//        return ResponseEntity.ok(userService.login(loginRequest));
//    }
//}
