package com.dingdong.picmap.domain.circle.mapper;

import com.dingdong.picmap.domain.circle.dto.request.CircleJoinRequestDto;
import com.dingdong.picmap.domain.circle.dto.request.CircleRequestDto;
import com.dingdong.picmap.domain.circle.entity.Circle;
import com.dingdong.picmap.domain.circle.entity.CircleUser;
import com.dingdong.picmap.domain.circle.repository.CircleRepository;
import com.dingdong.picmap.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CircleEntityMapper {

    private final CircleRepository circleRepository;
    private final UserRepository userRepository;

    public Circle toCircleEntity(CircleRequestDto circleRequestDto) {
        return Circle.builder()
                .name(circleRequestDto.getName())
                .description(circleRequestDto.getDescription())
                .status(circleRequestDto.getStatus())
                .build();
    }

    public List<CircleUser> toCircleUserEntity(CircleJoinRequestDto circleJoinRequestDto) {
        Circle circle = circleRepository.findById(circleJoinRequestDto.getCircleId()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 써클입니다."));
        return circleJoinRequestDto.getUserIds().stream()
                .map(userId -> userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다.")))
                // 이미 써클에 가입되어있는 유저인지 확인
                .filter(user -> !circleRepository.existsByCircleUser(circle, user))
                .map(user -> new CircleUser(circle, user))
                .collect(Collectors.toList());
    }

}
