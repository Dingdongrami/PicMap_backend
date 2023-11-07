package com.dingdong.picmap.domain.user.controller;

import com.dingdong.picmap.domain.user.service.UserOAuthService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="/user/login", produces = "application/json")
@AllArgsConstructor
public class UserOAuthController {

    private UserOAuthService userOAuthService;

    // google oauth2
    @GetMapping("/oauth2/code/{registrationId}")
    public void googleLogin(@RequestParam String code, @PathVariable String registrationId) {
        userOAuthService.socialLogin(code, registrationId);
    }

}
