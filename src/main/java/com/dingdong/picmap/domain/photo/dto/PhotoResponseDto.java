package com.dingdong.picmap.domain.photo.dto;

import com.dingdong.picmap.domain.photo.entity.Photo;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class PhotoResponseDto {
    private final Long id;
    private final String filePath;

    @Builder
    public PhotoResponseDto(Long id, String filePath) {
        this.id = id;
        this.filePath = filePath;
    }

    public static PhotoResponseDto of(Photo photo) {
    	return PhotoResponseDto.builder()
    			.id(photo.getId())
    			.filePath(photo.getFilePath())
    			.build();
    }

     public static List<PhotoResponseDto> listOf(List<Photo> photos) {
         return photos.stream()
                 .map(PhotoResponseDto::of)
                 .collect(Collectors.toList());
     }
}
