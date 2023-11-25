package com.dingdong.picmap.domain.circle.entity;

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
}
