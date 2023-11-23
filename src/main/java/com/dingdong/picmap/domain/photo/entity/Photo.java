package com.dingdong.picmap.domain.photo.entity;

import com.dingdong.picmap.domain.global.BaseTimeEntity;
import com.dingdong.picmap.domain.user.entity.User;
import jdk.jshell.Snippet;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Table(name = "photos")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Photo extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "photo_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @Column(name = "file_path")
    private String filePath;

    @Column(name = "shooting_date", columnDefinition = "TIMESTAMP")
    private LocalDateTime shootingDate;

    private Double latitude;
    private Double longitude;

    //== file path 설정 메서드 ==//
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    //== metadata 설정 메서드 ==//
    public void setMetaData(Double latitude, Double longitude, LocalDateTime shootingDate) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.shootingDate = shootingDate;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // toEntity
    public Photo toEntity() {
    	return Photo.builder()
    			.user(user)
    			.filePath(filePath)
    			.build();
    }
}