package com.dingdong.picmap.config.auth.dto;

import com.dingdong.picmap.domain.user.entity.User;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {
    private String name;
    private String email;
    private String picture;

    public SessionUser(User user) {
        this.name = user.getNickname();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }
}
