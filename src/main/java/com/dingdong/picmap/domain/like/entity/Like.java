package com.dingdong.picmap.domain.like.entity;

import com.dingdong.picmap.domain.global.BaseTimeEntity;
import com.dingdong.picmap.domain.photo.entity.Photo;
import com.dingdong.picmap.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "likes")
@NoArgsConstructor
@AllArgsConstructor
public class Like extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "photo_id")
    private Photo photo;

    @Builder
    public Like(User user, Photo photo) {
        this.user = user;
        this.photo = photo;
    }

    public static Like of(User user, Photo photo) {
        Like like = Like.builder()
                .user(user)
                .photo(photo)
                .build();
        photo.getLikes().add(like);
        return like;
    }
}
