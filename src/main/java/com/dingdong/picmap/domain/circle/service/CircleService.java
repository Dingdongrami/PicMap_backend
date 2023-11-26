package com.dingdong.picmap.domain.circle.service;

import com.dingdong.picmap.domain.circle.dto.CircleResponseDto;
import com.dingdong.picmap.domain.circle.entity.Circle;
import com.dingdong.picmap.domain.circle.repository.CircleRepository;
import com.dingdong.picmap.domain.circle.repository.CircleUserRepository;
import com.dingdong.picmap.domain.user.entity.User;
import com.dingdong.picmap.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CircleService {

    private final CircleRepository circleRepository;
    private final CircleUserRepository circleUserRepository;
    private final UserRepository userRepository;

    public List<CircleResponseDto> getCirclesByUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));
        List<Circle> circles = circleUserRepository.findCirclesByUserId(user);
        return circles.stream()
                .map(circle -> CircleResponseDto.builder()
                        .id(circle.getId())
                        .name(circle.getName())
                        .description(circle.getDescription())
                        .build())
                .collect(Collectors.toList());
    }

    public List<CircleResponseDto> getPublicCircles() {
        List<Circle> circles = circleRepository.findByStatus("PUBLIC");
        return circles.stream()
                .map(circle -> CircleResponseDto.builder()
                        .id(circle.getId())
                        .name(circle.getName())
                        .description(circle.getDescription())
                        .build())
                .collect(Collectors.toList());
    }
}
