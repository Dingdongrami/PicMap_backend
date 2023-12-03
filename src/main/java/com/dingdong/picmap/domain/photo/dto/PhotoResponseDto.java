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
    private Integer likeCount;
    private Integer commentCount;
    private Double latitude;
    private Double longitude;

    public PhotoResponseDto(Photo photo) {
        this.id = photo.getId();
        this.filePath = photo.getFilePath();
        this.likeCount = photo.getLikes().size();
        this.commentCount = photo.getComments().size();
        this.latitude = photo.getLatitude();
        this.longitude = photo.getLongitude();
    }

    public static PhotoResponseDto of(Photo photo) {
    	return PhotoResponseDto.builder()
    			.id(photo.getId())
    			.filePath(photo.getFilePath())
          .likeCount(photo.getLikes().size())
          .commentCount(photo.getComments().size())
          .latitude(photo.getLatitude())
          .longitude(photo.getLongitude())
    			.build();
    }

     public static List<PhotoResponseDto> listOf(List<Photo> photos) {
         return photos.stream()
                 .map(PhotoResponseDto::of)
                 .collect(Collectors.toList());
     }
}
