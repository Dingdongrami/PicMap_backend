package com.dingdong.picmap.domain.comment.entity;

import com.dingdong.picmap.domain.global.BaseTimeEntity;
import com.dingdong.picmap.domain.photo.entity.Photo;
import com.dingdong.picmap.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "comments")
@NoArgsConstructor
@AllArgsConstructor
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "photo_id")
    private Photo photo;

    @Builder
    public Comment(String comment, User user, Photo photo) {
        this.comment = comment;
        this.user = user;
        this.photo = photo;
    }

}
