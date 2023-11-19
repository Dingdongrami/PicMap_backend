package com.dingdong.picmap.domain.circle.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CircleCreateResponseDto {

    private Long id;
    private String name;
}
