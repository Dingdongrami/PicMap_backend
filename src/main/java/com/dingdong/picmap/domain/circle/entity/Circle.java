package com.dingdong.picmap.domain.circle.entity;

import com.dingdong.picmap.domain.circle.dto.request.CircleRequestDto;
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
    private String status;  // PUBLIC, PRIVATE, GOVERNMENT
    private String thumbnail;

    @Builder
    public Circle(String name, String description, String status, String thumbnail) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.thumbnail = thumbnail;
    }

    @Builder
    public Circle(String name, String description, String status) {
        this.name = name;
        this.description = description;
        this.status = status;
    }

    //==thumbnail 설정 메서드==//
    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public void update(Circle circle) {
        this.name = circle.getName();
        this.description = circle.getDescription();
        this.status = circle.getStatus();
    }

    public boolean getIsPublic() {
        return this.status.equals("PUBLIC");
    }

    public boolean getIsGovernment() {
        return this.status.equals("GOVERNMENT");
    }

    public void updateName(String name) {
        this.name = name;
    }
}
