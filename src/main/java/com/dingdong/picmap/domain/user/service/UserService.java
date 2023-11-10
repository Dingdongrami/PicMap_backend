package com.dingdong.picmap.domain.user.service;

import com.dingdong.picmap.domain.user.entity.User;
import com.dingdong.picmap.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // spring security
    private final BCryptPasswordEncoder encoder;

    // loginId 중복 체크 -> true: 중복
//    public boolean checkLoginIdDuplicate(String loginId) {
//        return userRepository.existsByLoginId(loginId);
//    }

    // nickname 중복 체크 -> true: 중복
    public boolean checkNicknameDuplicate(String nickname) {
        return userRepository.existsByNickname(nickname);
    }

//    public void join(JoinRequest joinRequest) {
//        userRepository.save(joinRequest.toEntity());
//    }

//    public void encodePasswordJoin(JoinRequest joinRequest) {
//        userRepository.save(joinRequest.toEntity(encoder.encode(joinRequest.getPassword())));
//    }

//    public User login(LoginRequest loginRequest) {
//        Optional<User> user = userRepository.findByLoginId(loginRequest.getLoginId());
//
//        if (user.isPresent()) {
//            if (encoder.matches(loginRequest.getPassword(), user.get().getPassword())) {
//                return user.get();
//            }
//        }
//        return null;
//    }

    public User getLoginUserByUserId(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

//    public User getLoginUserByLoginId(String loginId) {
//        return userRepository.findByLoginId(loginId).orElse(null);
//    }
}
