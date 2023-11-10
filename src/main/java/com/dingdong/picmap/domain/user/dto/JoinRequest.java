//package com.dingdong.picmap.domain.user.dto;
//
//import com.dingdong.picmap.domain.user.entity.User;
//import com.dingdong.picmap.domain.user.entity.enums.Role;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//@Getter
//@NoArgsConstructor
//public class JoinRequest {
//
//    private String loginId;
//    private String password;
//    private String nickname;
//
//    public User toEntity() {
//        return User.builder()
//                .loginId(this.loginId)
//                .password(this.password)
//                .nickname(this.nickname)
//                .role(Role.USER)
//                .build();
//    }
//
//    // password encoded
//    public User toEntity(String encodedPassword) {
//        return User.builder()
//                .loginId(this.loginId)
//                .password(encodedPassword)
//                .nickname(this.nickname)
//                .role(Role.USER)
//                .build();
//    }
//}
