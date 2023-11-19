package com.dingdong.picmap.domain.circle.dto;

public class CircleCreateResponseDto {

    private Long circleId;
    private String name;

    public CircleCreateResponseDto(Long circleId, String name) {
        this.circleId = circleId;
        this.name = name;
    }
}
