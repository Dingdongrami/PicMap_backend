package com.dingdong.picmap.domain.user.service;

import com.dingdong.picmap.config.jwt.JwtToken;
import com.dingdong.picmap.config.jwt.JwtTokenProvider;
import com.dingdong.picmap.config.util.RedisUtils;
import com.dingdong.picmap.config.util.SecurityUtils;
import com.dingdong.picmap.domain.friendship.entity.Friendship;
import com.dingdong.picmap.domain.friendship.repository.FriendshipRepository;
import com.dingdong.picmap.domain.photo.service.s3.S3Uploader;
import com.dingdong.picmap.domain.user.dto.request.LoginRequestDto;
import com.dingdong.picmap.domain.user.dto.request.SignupRequestDto;
import com.dingdong.picmap.domain.user.dto.request.UserUpdateRequestDto;
import com.dingdong.picmap.domain.user.dto.response.FriendsResponseDto;
import com.dingdong.picmap.domain.user.dto.response.UserProfileUpdateResponseDto;
import com.dingdong.picmap.domain.user.dto.response.UserResponseDto;
import com.dingdong.picmap.domain.user.dto.response.UserUpdateResponseDto;
import com.dingdong.picmap.domain.user.entity.User;
import com.dingdong.picmap.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final SecurityUtils securityUtils;
    private final RedisUtils redisUtils;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final UserValidateService userValidateService;
    private final S3Uploader s3Uploader;
    private final FriendshipRepository friendshipRepository;

    public Long signup(SignupRequestDto signupRequestDto) {
        User user = User.builder()
                .email(signupRequestDto.getEmail())
                .password(passwordEncoder.encode(signupRequestDto.getPassword()))
                .nickname(signupRequestDto.getNickname())
                .roles(Collections.singletonList("ROLE_USER"))
                .build();
        return userRepository.save(user).getId();
    }

    public JwtToken login(LoginRequestDto loginRequestDto) {

        final String requestEmail = loginRequestDto.getEmail();
        final String requestPassword = loginRequestDto.getPassword();

        User user = userRepository.findByEmail(requestEmail)
                .orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다."));
        userValidateService.validatePassword(requestPassword, user);
        Authentication authentication = securityUtils.setAuthentication(requestEmail, requestPassword);
        JwtToken jwtToken = jwtTokenProvider.generateToken(authentication);
        redisUtils.setValues(authentication.getName(), jwtToken.getRefreshToken(), Duration.ofDays(14));
        return jwtToken;
    }

    public UserResponseDto getUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("해당 유저가 없습니다."));

        List<Friendship> allFriendshipByUser = friendshipRepository.findAllFriendshipByUser(user);
        List<FriendsResponseDto> requestList = allFriendshipByUser.stream()
                .filter(friendship -> !friendship.isAccepted())
                .map(FriendsResponseDto::new)
                .collect(Collectors.toList());
        List<FriendsResponseDto> friendsList = allFriendshipByUser.stream()
                .filter(Friendship::isAccepted)
                .map(FriendsResponseDto::new)
                .collect(Collectors.toList());
        return new UserResponseDto(user, requestList, friendsList);
    }


    @Transactional
    public UserUpdateResponseDto update(Long userId, UserUpdateRequestDto userUpdateRequestDto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("해당 유저가 없습니다."));
        user.update(userUpdateRequestDto.getNickname(), userUpdateRequestDto.getIntroduce(), userUpdateRequestDto.getStatus());
        return new UserUpdateResponseDto(user);
    }

    @Transactional
    public UserProfileUpdateResponseDto updateProfile(Long userId, MultipartFile profileImage) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("해당 유저가 없습니다."));
        String profileImageUrl = s3Uploader.upload(profileImage, "profile");
        user.updateProfile(profileImageUrl);
        return new UserProfileUpdateResponseDto(user.getId(), profileImageUrl);
    }

    @Transactional
    public UserProfileUpdateResponseDto updateNoProfile(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("해당 유저가 없습니다."));
        user.updateProfile(null);
        return new UserProfileUpdateResponseDto(user.getId(), null);
    }

    public List<UserResponseDto> getAllUser() {
        List<User> users = userRepository.findAll();
        return UserResponseDto.listOf(users);
    }
}
