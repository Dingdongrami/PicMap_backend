package com.dingdong.picmap.domain.photo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PhotoUploadRequestDto {

    private Long userId;
    private Long circleId;
}
