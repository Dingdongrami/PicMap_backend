package com.dingdong.picmap.domain.search.dto;

import com.dingdong.picmap.domain.circle.entity.Circle;
import com.dingdong.picmap.domain.photo.dto.PhotoResponseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SearchLocationResponseDto {

    private Long circleId;
    private String circleName;
    private PhotoResponseDto photo;

    public SearchLocationResponseDto(Circle circle, PhotoResponseDto photoResponseDto) {
        this.circleId = circle.getId();
        this.circleName = circle.getName();
        this.photo = photoResponseDto;
    }
}
