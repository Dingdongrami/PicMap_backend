package com.dingdong.picmap.domain.user.service;

import com.dingdong.picmap.config.jwt.JwtToken;
import com.dingdong.picmap.config.jwt.JwtTokenProvider;
import com.dingdong.picmap.config.util.RedisUtils;
import com.dingdong.picmap.config.util.SecurityUtils;
import com.dingdong.picmap.domain.photo.service.S3Uploader;
import com.dingdong.picmap.domain.user.dto.request.LoginRequestDto;
import com.dingdong.picmap.domain.user.dto.request.SignupRequestDto;
import com.dingdong.picmap.domain.user.dto.request.UserUpdateRequestDto;
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
        return new UserResponseDto(user);
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
}
