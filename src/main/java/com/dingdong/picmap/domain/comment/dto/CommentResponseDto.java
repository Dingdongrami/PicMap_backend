package com.dingdong.picmap.domain.comment.dto;

import com.dingdong.picmap.domain.comment.entity.Comment;
import lombok.Getter;

@Getter
public class CommentResponseDto {

    private Long id;
    private String comment;
    private Long userId;

    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.comment = comment.getComment();
        this.userId = comment.getUser().getId();
    }
}
