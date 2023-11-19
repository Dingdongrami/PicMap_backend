package com.dingdong.picmap.domain.circle.service;

import com.dingdong.picmap.config.util.UserUtils;
import com.dingdong.picmap.domain.circle.dto.CircleCreateRequestDto;
import com.dingdong.picmap.domain.circle.dto.CircleCreateResponseDto;
import com.dingdong.picmap.domain.circle.entity.Circle;
import com.dingdong.picmap.domain.circle.entity.CircleUser;
import com.dingdong.picmap.domain.circle.repository.CircleRepository;
import com.dingdong.picmap.domain.circle.repository.CircleUserRepository;
import com.dingdong.picmap.domain.user.entity.User;
import com.dingdong.picmap.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CircleCreateService {

    private final CircleRepository circleRepository;
    private final CircleUserRepository circleUserRepository;
    private final UserRepository userRepository;
    private final UserUtils userUtils;

    // 써클 생성
    public CircleCreateResponseDto createCircle(CircleCreateRequestDto request) {
        User user = userRepository.findById(request.getUserId()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));
        Circle circle = circleRepository.save(request.toEntity());
        addCircleMember(circle, user);
//        addCircleMember(circle, userUtils.getUser());
        return new CircleCreateResponseDto(circle.getId(), circle.getName());
    }

    public void addCircleMember(Circle circle, User user) {
        circleUserRepository.save(new CircleUser(circle, user));
    }
}
