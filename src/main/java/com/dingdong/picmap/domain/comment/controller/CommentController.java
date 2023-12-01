package com.dingdong.picmap.domain.comment.controller;

import com.dingdong.picmap.domain.comment.dto.CommentRequestDto;
import com.dingdong.picmap.domain.comment.dto.CommentResponseDto;
import com.dingdong.picmap.domain.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/add")
    public ResponseEntity<CommentResponseDto> addComment(@RequestBody CommentRequestDto requestDto) {
        return ResponseEntity.ok(commentService.addComment(requestDto));
    }

    @GetMapping("/list")
    public ResponseEntity<List<CommentResponseDto>> getCommentList(@RequestParam Long photoId) {
        return ResponseEntity.ok(commentService.getCommentList(photoId));
    }


}