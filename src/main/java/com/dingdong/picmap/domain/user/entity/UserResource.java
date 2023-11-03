package com.dingdong.picmap.domain.user.entity;

import lombok.Getter;

@Getter
public class UserResource {

    private final String id;
    private final String email;
    private final String nickname;

    public UserResource(String id, String email, String nickname) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
    }
}
