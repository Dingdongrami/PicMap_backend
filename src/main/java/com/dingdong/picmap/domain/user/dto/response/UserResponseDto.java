package com.dingdong.picmap.domain.user.dto.response;

import com.dingdong.picmap.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class UserResponseDto {

    private Long id;
    private String nickname;
    private String email;
    private String profileImage;
    private String introduce;
    private String status;
    private List<FriendsResponseDto> requestList;
    private List<FriendsResponseDto> friends;

    public UserResponseDto(User user, List<FriendsResponseDto> requestList, List<FriendsResponseDto> friends) {
        this.id = user.getId();
        this.nickname = user.getNickname();
        this.email = user.getEmail();
        this.profileImage = user.getProfileImage();
        this.introduce = user.getIntroduce();
        this.status = user.getStatus();
        this.requestList = requestList;
        this.friends = friends;
    }

    public UserResponseDto(User user) {
        this.id = user.getId();
        this.nickname = user.getNickname();
        this.email = user.getEmail();
        this.profileImage = user.getProfileImage();
        this.introduce = user.getIntroduce();
        this.status = user.getStatus();
    }

    public static List<UserResponseDto> listOf(List<User> users) {
        return users.stream()
                .map(UserResponseDto::new)
                .collect(Collectors.toList());
    }
}
