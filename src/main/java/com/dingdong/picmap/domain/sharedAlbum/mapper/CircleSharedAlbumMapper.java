package com.dingdong.picmap.domain.sharedAlbum.mapper;

import com.dingdong.picmap.domain.circle.entity.Circle;
import com.dingdong.picmap.domain.photo.dto.PhotoUploadRequestDto;
import com.dingdong.picmap.domain.photo.entity.Photo;
import com.dingdong.picmap.domain.sharedAlbum.entity.CircleSharedAlbum;
import com.dingdong.picmap.domain.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class CircleSharedAlbumMapper {

    public CircleSharedAlbum createCircleSharedAlbum(Photo photo, Circle circle, User user) {
        return CircleSharedAlbum.builder()
                .photo(photo)
                .circle(circle)
                .user(user)
                .build();
    }
}
