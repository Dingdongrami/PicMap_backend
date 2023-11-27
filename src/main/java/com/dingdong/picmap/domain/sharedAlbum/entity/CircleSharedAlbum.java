package com.dingdong.picmap.domain.sharedAlbum.entity;

import com.dingdong.picmap.domain.circle.entity.Circle;
import com.dingdong.picmap.domain.photo.entity.Photo;
import com.dingdong.picmap.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "circle_shared_albums")
@NoArgsConstructor
public class CircleSharedAlbum {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "circle_shared_album_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "circle_id")
    private Circle circle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "photo_id")
    private Photo photo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public CircleSharedAlbum(Circle circle, Photo photo, User user) {
        this.circle = circle;
        this.photo = photo;
        this.user = user;
    }
}
