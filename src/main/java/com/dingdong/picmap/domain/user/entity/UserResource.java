package com.dingdong.picmap.domain.user.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserResource {

    private final String id;
    private final String email;
    private final String nickname;

}
