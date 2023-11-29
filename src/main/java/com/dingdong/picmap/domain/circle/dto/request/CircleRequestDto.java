package com.dingdong.picmap.domain.circle.dto.request;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CircleRequestDto {

    private Long id;
    private String name;
    private String description;
    private String status;
}
