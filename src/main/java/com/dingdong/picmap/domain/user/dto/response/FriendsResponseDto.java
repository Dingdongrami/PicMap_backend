package com.dingdong.picmap.domain.user.dto.response;

import com.dingdong.picmap.domain.friendship.entity.Friendship;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FriendsResponseDto {

    private Long userId;
    private String nickname;
    private String profileImage;


    public FriendsResponseDto(Friendship friendship) {
        this.userId = friendship.getRequester().getId();
        this.nickname = friendship.getRequester().getNickname();
        this.profileImage = friendship.getRequester().getProfileImage();
    }
}
