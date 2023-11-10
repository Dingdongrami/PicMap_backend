package com.dingdong.picmap.config.auth;

import com.dingdong.picmap.domain.user.entity.enums.Role;
import com.dingdong.picmap.domain.user.service.CustomOAuth2UserService;
import com.dingdong.picmap.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final UserService userService;

    @Bean
    public static BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // oauth2login request 들어오면 customOAuth2UserService 로 리다이렉트
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity
//            .csrf().disable()
//            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//            .and()
//            .formLogin().disable()
//                .httpBasic().disable()
////            .usernameParameter("loginId")
////            .passwordParameter("password")
////            .loginPage("/user/login")
////            .defaultSuccessUrl("/user/login/result")
//            .authorizeRequests()
////            .antMatchers("/user/login/**", "/user/signup/**").permitAll()
//            .antMatchers("/security-login/**", "/user/login").permitAll()
//            .antMatchers("/api/**").hasRole("USER")
//            .anyRequest().authenticated()
//            .and()
////            .logout()
////            .logoutUrl("/user/logout")
////            .invalidateHttpSession(true)
////            .and()
//            .oauth2Login().userInfoEndpoint()
//            .userService(customOAuth2UserService);
//        return httpSecurity.build();
////        return httpSecurity.httpBasic().disable()
////                .csrf().disable()
////                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//    }

    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
                .headers().frameOptions().disable()
            .and()
                .authorizeRequests()
                .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**", "/profile").permitAll()
                .antMatchers("/api/v1/**").hasRole(Role.USER.name())
                .anyRequest().authenticated()
            .and()
                .logout()
                    .logoutSuccessUrl("/")
            .and()
                .oauth2Login()
                    .userInfoEndpoint()
                        .userService(customOAuth2UserService);
    }
}
