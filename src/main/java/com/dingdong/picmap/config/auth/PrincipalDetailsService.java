//package com.dingdong.picmap.config.auth;
//
//import com.dingdong.picmap.domain.user.entity.User;
//import com.dingdong.picmap.domain.user.repository.UserRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class PrincipalDetailsService implements UserDetailsService {
//
//    private final UserRepository userRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user = userRepository.findByLoginId(username)
//                .orElseThrow(() -> {
//                    return new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다.");
//        });
//        return new PrincipalDetails(user);
//    }
//}
