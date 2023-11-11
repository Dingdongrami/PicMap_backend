package com.dingdong.picmap.domain.photo.entity;

import com.dingdong.picmap.domain.global.BaseTimeEntity;
import com.dingdong.picmap.domain.user.entity.User;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

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
    private Date shootingDate;

    private Double latitude;
    private Double longitude;

    //== file path 설정 메서드 ==//
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    //== shooting date 설정 메서드 ==//
    public void setShootingDate(Date shootingDate) {
        this.shootingDate = shootingDate;
    }

    //== gps 설정 메서드 ==//
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}