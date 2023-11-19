package com.dingdong.picmap.domain.circle.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CircleResponseDto {
    private Long id;
    private String name;
}
