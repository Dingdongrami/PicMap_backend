package com.dingdong.picmap.domain.circle.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
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
}
