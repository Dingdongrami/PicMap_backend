package com.dingdong.picmap.domain.user.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Role {

    private final String key;
    private final String title;

    public static final Role USER = new Role("ROLE_USER", "일반 사용자");
    public static final Role ADMIN = new Role("ROLE_ADMIN", "관리자");
}
