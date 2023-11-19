package com.dingdong.picmap.domain.circle.entity;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "circles")
public class Circle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "circle_id")
    private Long id;
    private String name;
    private String description;
    private String status;  // PUBLIC, PRIVATE

    @Builder
    public Circle(String name, String description, String status) {
        this.name = name;
        this.description = description;
        this.status = status;
    }
}
