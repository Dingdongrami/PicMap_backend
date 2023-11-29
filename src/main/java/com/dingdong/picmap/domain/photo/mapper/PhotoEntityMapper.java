package com.dingdong.picmap.domain.photo.mapper;

import com.dingdong.picmap.domain.photo.entity.Photo;
import com.dingdong.picmap.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PhotoEntityMapper {

    public static Photo toPhotoEntity(User user, String filePath) {
        return new Photo(user, filePath);
    }
}
