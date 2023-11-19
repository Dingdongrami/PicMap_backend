package com.dingdong.picmap.domain.photo.dto;

import com.dingdong.picmap.domain.photo.entity.Photo;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class PhotoResponse {
    private final Long photoId;
    private final String filePath;

    @Builder
    public PhotoResponse(Long photoId, String filePath) {
        this.photoId = photoId;
        this.filePath = filePath;
    }

    public static PhotoResponse of(Long photoId, String filePath) {
    	return PhotoResponse.builder()
    			.photoId(photoId)
    			.filePath(filePath)
    			.build();
    }

     public static List<PhotoResponse> listOf(List<Photo> photos) {
         return photos.stream()
                 .map(photo -> PhotoResponse.of(photo.getId(), photo.getFilePath()))
                 .collect(Collectors.toList());
     }
}
