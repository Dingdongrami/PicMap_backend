package com.dingdong.picmap.domain.circle.service;

import com.dingdong.picmap.domain.circle.dto.request.CircleJoinRequestDto;
import com.dingdong.picmap.domain.circle.dto.request.CircleLeaveRequestDto;
import com.dingdong.picmap.domain.circle.dto.request.CircleRequestDto;
import com.dingdong.picmap.domain.circle.dto.response.CircleResponseDto;
import com.dingdong.picmap.domain.circle.dto.response.CircleUserResponseDto;
import com.dingdong.picmap.domain.circle.entity.Circle;
import com.dingdong.picmap.domain.circle.entity.CircleUser;
import com.dingdong.picmap.domain.circle.mapper.CircleEntityMapper;
import com.dingdong.picmap.domain.circle.repository.CircleRepository;
import com.dingdong.picmap.domain.circle.repository.CircleUserRepository;
import com.dingdong.picmap.domain.user.dto.response.UserResponseDto;
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
    private final CircleEntityMapper circleEntityMapper;

    public List<CircleResponseDto> getCirclesByUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));
        List<Circle> circles = circleUserRepository.findCirclesByUserId(user);
        return circles.stream()
                .map(CircleResponseDto::new)
                .collect(Collectors.toList());
    }

    public List<CircleResponseDto> getPublicCircles() {
        List<Circle> circles = circleRepository.findPublicAndGovernmentCircles();
        return circles.stream()
                .map(CircleResponseDto::new)
                .collect(Collectors.toList());
    }

    public CircleResponseDto getCircle(Long circleId) {
        Circle circle = circleRepository.findById(circleId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 써클입니다."));
        return new CircleResponseDto(circle);
    }

    @Transactional
    public CircleResponseDto updateCircleName(Long circleId, String name) {
        Circle circle = circleRepository.findById(circleId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 써클입니다."));
        circle.updateName(name);
        return new CircleResponseDto(circle);
    }

    public CircleUserResponseDto getCircleMembers(Long circleId) {
        Circle circle = circleRepository.findById(circleId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 써클입니다."));
        List<User> users = circleUserRepository.findUsersByCircle(circle);
        List<UserResponseDto> userResponseDtoList = users.stream()
                .map(UserResponseDto::new)
                .collect(Collectors.toList());
        return new CircleUserResponseDto(circle, userResponseDtoList);
    }

    @Transactional
    public void joinCircle(CircleJoinRequestDto requestDto) {
        Circle circle = circleRepository.findById(requestDto.getCircleId()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 써클입니다."));
        User user = userRepository.findById(requestDto.getUserId()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));

        if (circleUserRepository.existsByCircleAndUser(circle, user)) {
            throw new IllegalArgumentException("이미 가입된 써클입니다.");
        }
        circleUserRepository.save(new CircleUser(circle, user));
    }

    @Transactional
    public void leaveCircle(CircleLeaveRequestDto requestDto) {

        Circle circle = circleRepository.findById(requestDto.getCircleId()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 써클입니다."));
        List<User> users = requestDto.getUserIdList().stream()
                .map(userId -> userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다.")))
                .collect(Collectors.toList());

        users.stream()
                .filter(user -> circleUserRepository.existsByCircleAndUser(circle, user))
                .map(user -> circleUserRepository.findCircleUserByCircleAndUser(user, circle).orElseThrow(() -> new IllegalArgumentException("해당 써클에 존재하지 않는 유저입니다.")))
                .forEach(circleUserRepository::delete);
    }

    public List<CircleResponseDto> getGovernmentCircles() {
        List<Circle> circles = circleRepository.findByStatus("GOVERNMENT");
        return circles.stream()
                .map(CircleResponseDto::new)
                .collect(Collectors.toList());
    }
}
