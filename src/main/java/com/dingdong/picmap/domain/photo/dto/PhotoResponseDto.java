package com.dingdong.picmap.domain.photo.dto;

import com.dingdong.picmap.domain.photo.entity.Photo;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class PhotoResponseDto {
    private final Long photoId;
    private final String filePath;

    @Builder
    public PhotoResponseDto(Long photoId, String filePath) {
        this.photoId = photoId;
        this.filePath = filePath;
    }

    public static PhotoResponseDto of(Long photoId, String filePath) {
    	return PhotoResponseDto.builder()
    			.photoId(photoId)
    			.filePath(filePath)
    			.build();
    }

     public static List<PhotoResponseDto> listOf(List<Photo> photos) {
         return photos.stream()
                 .map(photo -> PhotoResponseDto.of(photo.getId(), photo.getFilePath()))
                 .collect(Collectors.toList());
     }
}
