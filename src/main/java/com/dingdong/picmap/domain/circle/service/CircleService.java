package com.dingdong.picmap.domain.circle.service;

import com.dingdong.picmap.domain.circle.dto.CircleRequestDto;
import com.dingdong.picmap.domain.circle.dto.CircleResponseDto;
import com.dingdong.picmap.domain.circle.entity.Circle;
import com.dingdong.picmap.domain.circle.repository.CircleRepository;
import com.dingdong.picmap.domain.circle.repository.CircleUserRepository;
import com.dingdong.picmap.domain.user.entity.User;
import com.dingdong.picmap.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
                .map(CircleResponseDto::new)
                .collect(Collectors.toList());
    }

    public List<CircleResponseDto> getPublicCircles() {
        List<Circle> circles = circleRepository.findByStatus("PUBLIC");
        return circles.stream()
                .map(CircleResponseDto::new)
                .collect(Collectors.toList());
    }

    public CircleResponseDto getCircle(Long circleId) {
        Circle circle = circleRepository.findById(circleId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 써클입니다."));
        return new CircleResponseDto(circle);
    }

    @Transactional
    public CircleResponseDto updateCircle(Long circleId, CircleRequestDto circleRequestDto) {
        Circle circle = circleRepository.findById(circleId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 써클입니다."));
        circle.update(circleRequestDto);
        return new CircleResponseDto(circle);
    }
}
