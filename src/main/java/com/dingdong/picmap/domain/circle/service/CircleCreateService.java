package com.dingdong.picmap.domain.circle.service;

import com.dingdong.picmap.config.util.UserUtils;
import com.dingdong.picmap.domain.circle.dto.request.CircleCreateRequestDto;
import com.dingdong.picmap.domain.circle.dto.request.CircleJoinRequestDto;
import com.dingdong.picmap.domain.circle.dto.response.CircleResponseDto;
import com.dingdong.picmap.domain.circle.entity.Circle;
import com.dingdong.picmap.domain.circle.entity.CircleUser;
import com.dingdong.picmap.domain.circle.repository.CircleRepository;
import com.dingdong.picmap.domain.circle.repository.CircleUserRepository;
import com.dingdong.picmap.domain.friendship.repository.FriendshipRepository;
import com.dingdong.picmap.domain.friendship.service.FriendshipService;
import com.dingdong.picmap.domain.photo.service.s3.S3Uploader;
import com.dingdong.picmap.domain.user.entity.User;
import com.dingdong.picmap.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class CircleCreateService {

    private final CircleRepository circleRepository;
    private final CircleUserRepository circleUserRepository;
    private final UserRepository userRepository;
    private final S3Uploader s3Uploader;
    private final UserUtils userUtils;
    private final FriendshipRepository friendshipRepository;

    // 써클 생성
    public CircleResponseDto createCircle(CircleCreateRequestDto request, MultipartFile thumbnail) throws IOException {
        User user = userRepository.findById(request.getUserId()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));
        Circle circle = circleRepository.save(request.toEntity());
        addThumbnail(circle.getId(), thumbnail);
        addCircleMember(circle, user);
//        addCircleMember(circle, userUtils.getUser());
        return new CircleResponseDto(circle);
    }

    public void addCircleMember(Circle circle, User user) {
        circleUserRepository.save(new CircleUser(circle, user));
    }

    public CircleResponseDto addThumbnail(Long circleId, MultipartFile file) throws IOException {
        Circle circle = circleRepository.findById(circleId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 써클입니다."));
        String thumbnailFilePath = s3Uploader.upload(file, "thumbnail");
        circle.setThumbnail(thumbnailFilePath);
        circleRepository.save(circle);
        return new CircleResponseDto(circle);
    }

    public String privateCircleJoin(CircleJoinRequestDto requestDto) {
        Circle circle = circleRepository.findById(requestDto.getCircleId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 써클입니다."));
        if (circle.getStatus().equals("PUBLIC")) {
            throw new IllegalArgumentException("비공개 써클이 아닙니다.");
        }
        User user = userRepository.findById(requestDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));

        if (circleUserRepository.existsByCircleAndUser(circle, user)) {
            throw new IllegalArgumentException("이미 가입한 써클입니다.");
        }
        // circle 의 유저와 친구인지 확인
        if (circleUserRepository.findUsersByCircle(circle)
                .stream()
                .flatMap(u -> friendshipRepository.findByRequesterAndReceiver(u, user) != null
                        ? Stream.of(u)
                        : Stream.empty()).findAny().isEmpty()) {
            throw new IllegalArgumentException("써클의 유저와 친구가 아닙니다.");
        }
        circleUserRepository.save(new CircleUser(circle, user));
        return "success";
    }
}
