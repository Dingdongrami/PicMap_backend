package com.dingdong.picmap.domain.comment.dto;

import lombok.Getter;

@Getter
public class CommentUpdateRequestDto {

    private Long commentId;
    private String comment;
}
