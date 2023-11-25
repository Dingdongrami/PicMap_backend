package com.dingdong.picmap.domain.photo.dto;

import com.dingdong.picmap.domain.photo.entity.Photo;
import com.dingdong.picmap.domain.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PhotoUploadRequestDto {

    private Long userId;
    private Long circleId;

    public static Photo toEntity(User user, String filePath) {
        return new Photo(user, filePath);
    }
}
