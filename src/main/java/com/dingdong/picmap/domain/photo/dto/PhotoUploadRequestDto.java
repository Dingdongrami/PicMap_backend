package com.dingdong.picmap.domain.photo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PhotoUploadRequestDto {

    private Double latitude;
    private Double longitude;
    private String shootingDate;
}
