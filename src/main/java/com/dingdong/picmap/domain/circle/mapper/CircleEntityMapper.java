package com.dingdong.picmap.domain.circle.mapper;

import com.dingdong.picmap.domain.circle.dto.request.CircleRequestDto;
import com.dingdong.picmap.domain.circle.entity.Circle;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CircleEntityMapper {

    public Circle toCircleEntity(CircleRequestDto circleRequestDto) {
        return Circle.builder()
                .name(circleRequestDto.getName())
                .description(circleRequestDto.getDescription())
                .status(circleRequestDto.getStatus())
                .build();
    }


}
