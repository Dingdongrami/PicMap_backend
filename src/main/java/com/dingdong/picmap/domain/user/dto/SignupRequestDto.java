package com.dingdong.picmap.domain.user.dto;

import lombok.Getter;

@Getter
public class SignupRequestDto {

    private String email;
    private String password;
    private String nickname;
}
