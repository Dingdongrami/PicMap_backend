package com.dingdong.picmap.domain.photo.dto;

import com.dingdong.picmap.domain.photo.entity.Photo;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PhotoResponseDto {
    private Long id;
    private String filePath;

    public PhotoResponseDto(Photo photo) {
        this.id = photo.getId();
        this.filePath = photo.getFilePath();
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
