package com.dingdong.picmap.domain.photo.mapper;

import com.dingdong.picmap.domain.photo.dto.PhotoUploadRequestDto;
import com.dingdong.picmap.domain.photo.entity.Photo;
import com.dingdong.picmap.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class PhotoEntityMapper {

    public Photo toPhotoEntity(User user, PhotoUploadRequestDto requestDto) {
        return Photo.builder()
                .user(user)
                .latitude(requestDto.getLatitude())
                .longitude(requestDto.getLongitude())
                .shootingDate(LocalDateTime.parse(requestDto.getShootingDate()))
                .build();
    }
}
