package com.dingdong.picmap.domain.circle.dto.response;

import com.dingdong.picmap.domain.circle.entity.Circle;
import com.dingdong.picmap.domain.user.dto.response.UserResponseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class CircleUserResponseDto {

    private Long circleId;
    private String circleName;
    private List<UserResponseDto> users;

    public CircleUserResponseDto(Circle circle, List<UserResponseDto> users) {
        this.circleId = circle.getId();
        this.circleName = circle.getName();
        this.users = users;
    }
}
