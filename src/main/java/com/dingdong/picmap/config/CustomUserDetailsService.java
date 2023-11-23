package com.dingdong.picmap.config;

import com.dingdong.picmap.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String id)  {
        return userRepository.findByEmail(id)
                .map(this::createUserDetails)
                .orElseThrow(()-> new UsernameNotFoundException(id + "-> 데이터베이스에서 찾을 수 없습니다."));
    }

    private UserDetails createUserDetails(com.dingdong.picmap.domain.user.entity.User user){
        GrantedAuthority grantedAuthority =
                new SimpleGrantedAuthority("ROLE_USER");
        return new User(user.getEmail(), user.getPassword(), Collections.singleton(grantedAuthority));
    }
}
