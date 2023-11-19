package com.dingdong.picmap.domain.user.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(name = "profile_image")
    private String profileImage;

    private String introduce;

    private String status;  // PUBLIC, PRIVATE

    @Builder
    public User(String nickname, String email, String profileImage, String introduce, String status) {
        this.nickname = nickname;
        this.email = email;
        this.profileImage = profileImage;
        this.introduce = introduce;
        this.status = status;
    }

    public User update(String nickname, String profileImage, String introduce, String status) {
        this.nickname = nickname;
        this.profileImage = profileImage;
        this.introduce = introduce;
        this.status = status;
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
