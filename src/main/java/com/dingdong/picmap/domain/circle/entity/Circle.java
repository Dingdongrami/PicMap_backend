package com.dingdong.picmap.domain.circle.entity;

import com.dingdong.picmap.domain.circle.dto.CircleRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "circles")
@NoArgsConstructor
public class Circle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "circle_id")
    private Long id;
    private String name;
    private String description;
    private String status;  // PUBLIC, PRIVATE
    private String thumbnail;

    @Builder
    public Circle(String name, String description, String status, String thumbnail) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.thumbnail = thumbnail;
    }

    //==thumbnail 설정 메서드==//
    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public void update(CircleRequestDto circleRequestDto) {
        this.name = circleRequestDto.getName();
        this.description = circleRequestDto.getDescription();
        this.status = circleRequestDto.getStatus();
    }
}
