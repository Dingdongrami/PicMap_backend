package com.dingdong.picmap.domain.photo.entity;

import com.dingdong.picmap.domain.global.BaseTimeEntity;
import com.dingdong.picmap.domain.user.entity.User;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Table(name = "photos")
@Entity
public class Photo extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "photo_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "file_path")
    private String filePath;

    @Column(name = "shooting_date")
    private LocalDateTime shootingDate;

    private String latitude;
    private String longitude;

    //== file path 설정 메서드 ==//
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

}