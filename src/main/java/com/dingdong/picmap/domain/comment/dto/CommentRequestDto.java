package com.dingdong.picmap.domain.comment.dto;

import lombok.Getter;

@Getter
public class CommentRequestDto {

    private Long userId;
    private Long photoId;
    private String comment;
}
