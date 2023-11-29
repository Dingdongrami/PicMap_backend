package com.dingdong.picmap.domain.photo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class PhotoLocationResponseDto {

    private final Long id;
    private final String filePath;
    private final Double latitude;
    private final Double longitude;

}
