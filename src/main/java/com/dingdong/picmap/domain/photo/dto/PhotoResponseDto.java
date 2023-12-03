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
        this.likeCount = photo.getLikeCount();
        this.commentCount = photo.getCommentCount();
        this.latitude = photo.getLatitude();
        this.longitude = photo.getLongitude();
    }

    @Builder
    public static PhotoResponseDto of(Photo photo) {
    	return PhotoResponseDto.builder()
    			.id(photo.getId())
    			.filePath(photo.getFilePath())
          .likeCount(photo.getLikeCount())
          .commentCount(photo.getCommentCount())
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
