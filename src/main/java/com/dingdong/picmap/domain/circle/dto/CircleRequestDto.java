package com.dingdong.picmap.domain.circle.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CircleRequestDto {

    private String name;
    private String description;
    private String status;
}
