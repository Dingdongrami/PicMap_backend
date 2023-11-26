package com.dingdong.picmap.domain.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserProfileUpdateResponseDto {

    private Long id;
    private String filePath;

}
