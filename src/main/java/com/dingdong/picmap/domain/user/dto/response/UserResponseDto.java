package com.dingdong.picmap.domain.user.dto.response;

import com.dingdong.picmap.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserResponseDto {

    private Long id;
    private String nickname;
    private String email;
    private String profileImage;
    private String introduce;
    private String status;

    public UserResponseDto(User user) {
        this.id = user.getId();
        this.nickname = user.getNickname();
        this.email = user.getEmail();
        this.profileImage = user.getProfileImage();
        this.introduce = user.getIntroduce();
        this.status = user.getStatus();
    }

}
