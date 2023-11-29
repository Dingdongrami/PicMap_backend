package com.dingdong.picmap.domain.circle.dto.request;

import com.dingdong.picmap.domain.circle.entity.Circle;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CircleCreateRequestDto {

    private Long userId;
    private String name;
    private String description;
    private String status;  // PUBLIC, PRIVATE

    public CircleCreateRequestDto(String name, String description, String status) {
        this.name = name;
        this.description = description;
        this.status = status;
    }

    public Circle toEntity() {
        return Circle.builder()
                .name(name)
                .description(description)
                .status(status)
                .build();
    }
}
