package com.dingdong.picmap.domain.photo.entity;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Table(name = "photos")
@Entity
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "photo_id")
    private Long id;

//    @JoinColumn(name = "user_id")
//    private User user;

    @Column(name = "file_path")
    private String filePath;

    @Column(name = "shooting_date")
    private LocalDateTime shootingDate;

    private String latitude;
    private String longitude;
}