package com.dingdong.picmap.domain.circle.dto.response;

import com.dingdong.picmap.domain.circle.entity.Circle;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CircleResponseDto {
    private Long id;
    private String name;
    private String description;
    private String status;
    private String thumbnail;

    @Builder
    public CircleResponseDto(Long id, String name, String description, String status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
    }

    public CircleResponseDto(Circle circle) {
        this.id = circle.getId();
        this.name = circle.getName();
        this.description = circle.getDescription();
        this.status = circle.getStatus();
        this.thumbnail = circle.getThumbnail();
    }
}
