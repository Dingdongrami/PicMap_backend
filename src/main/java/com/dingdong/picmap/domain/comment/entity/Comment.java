package com.dingdong.picmap.domain.comment.entity;

import com.dingdong.picmap.domain.global.BaseTimeEntity;
import com.dingdong.picmap.domain.photo.entity.Photo;
import com.dingdong.picmap.domain.user.entity.User;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "comments")
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "photo_id")
    private Photo photo;
}
