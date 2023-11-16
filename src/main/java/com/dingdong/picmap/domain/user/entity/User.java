package com.dingdong.picmap.domain.user.entity;

import com.dingdong.picmap.domain.user.entity.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

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

    private String picture;
    private String password;

    @Builder
    public User(String nickname, String email, String picture) {
        this.nickname = nickname;
        this.email = email;
        this.picture = picture;
    }

    public User update(String nickname, String picture) {
        this.nickname = nickname;
        this.picture = picture;
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
