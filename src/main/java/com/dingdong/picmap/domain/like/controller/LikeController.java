package com.dingdong.picmap.domain.like.controller;

import com.dingdong.picmap.domain.like.dto.LikeRequestDto;
import com.dingdong.picmap.domain.like.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/likes")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    // 좋아요 추가
    @PostMapping("/add")
    public ResponseEntity<?> addLike(@RequestBody LikeRequestDto requestDto) {
        likeService.addLike(requestDto);
        return ResponseEntity.ok().build();
    }

    // 좋아요 취소
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteLike(@RequestBody LikeRequestDto requestDto) {
        likeService.deleteLike(requestDto);
        return ResponseEntity.ok().build();
    }
}
