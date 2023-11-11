//package com.dingdong.picmap.config.auth;
//
//import com.dingdong.picmap.domain.user.entity.User;
//import com.dingdong.picmap.domain.user.entity.enums.Role;
//import lombok.Builder;
//import lombok.Getter;
//
//import java.util.Map;
//
//@Getter
//@Builder
//public class OAuthAttributes {
//
//    private Map<String, Object> attributes;
//    private String nameAttributeKey;
//    private String name;
//    private String email;
//    private String picture;
//
//    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
////        if ("naver".equals(registrationId)) {
////            return ofNaver("id", attributes);
////        } else if ("kakao".equals(registrationId)) {
////            return ofKakao("id", attributes);
////        }
//        return ofGoogle(userNameAttributeName, attributes);
//    }
//
//    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
//        return OAuthAttributes.builder()
//                .name((String) attributes.get("name"))
//                .email((String) attributes.get("email"))
//                .picture((String) attributes.get("picture"))
//                .attributes(attributes)
//                .nameAttributeKey(userNameAttributeName)
//                .build();
//    }
//
//    private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
//        Map<String, Object> response = (Map<String, Object>) attributes.get("response");
//
//        return OAuthAttributes.builder()
//                .name((String) response.get("name"))
//                .email((String) response.get("email"))
//                .picture((String) response.get("profile_image"))
//                .attributes(response)
//                .nameAttributeKey(userNameAttributeName)
//                .build();
//    }
//
//    private static OAuthAttributes ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
//        Map<String, Object> kakao_account = (Map<String, Object>) attributes.get("kakao_account");
//
//        return OAuthAttributes.builder()
//                .name((String) kakao_account.get("nickname"))
//                .email((String) kakao_account.get("email"))
//                .picture((String) kakao_account.get("profile_image_url"))
//                .attributes(kakao_account)
//                .nameAttributeKey(userNameAttributeName)
//                .build();
//    }
//
//    public User toEntity() {
//        return User.builder()
//                .nickname(name)
//                .email(email)
//                .role(Role.USER)
//                .build();
//    }
//}
