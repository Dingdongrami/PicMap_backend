package com.dingdong.picmap.domain.circle.controller;

import com.dingdong.picmap.domain.circle.dto.CircleCreateRequestDto;
import com.dingdong.picmap.domain.circle.dto.CircleCreateResponseDto;
import com.dingdong.picmap.domain.circle.dto.CircleResponseDto;
import com.dingdong.picmap.domain.circle.service.CircleCreateService;
import com.dingdong.picmap.domain.circle.service.CircleService;
import com.dingdong.picmap.domain.global.BaseTimeEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/circles")
public class CircleController extends BaseTimeEntity {

    private final CircleCreateService circleCreateService;
    private final CircleService circleService;

    // 써클 생성
    @PostMapping("/add-circle")
    public ResponseEntity<CircleResponseDto> createCircle(@RequestBody CircleCreateRequestDto request) {
        return ResponseEntity.ok(circleCreateService.createCircle(request));
    }

    // 써클 thumbnail 등록
    @PostMapping("/{circleId}/add-thumbnail")
    public ResponseEntity<CircleResponseDto> addThumbnail(@PathVariable Long circleId, @RequestPart("thumbnail") MultipartFile file) throws IOException {
        return ResponseEntity.ok(circleCreateService.addThumbnail(circleId, file));
    }

    // 써클 조회
    @GetMapping("/{circleId}")
    public ResponseEntity<CircleResponseDto> getCircle(@PathVariable Long circleId) {
        return ResponseEntity.ok(circleService.getCircle(circleId));
    }

    // 해당 user 가 가입된 써클 리스트 조회
    @GetMapping("/list/{userId}")
    public ResponseEntity<List<CircleResponseDto>> getCircles(@PathVariable Long userId) {
        return ResponseEntity.ok(circleService.getCirclesByUser(userId));
    }

    // public 한 써클 리스트 조회
    @GetMapping("/public")
    public ResponseEntity<List<CircleResponseDto>> getPublicCircles() {
        return ResponseEntity.ok(circleService.getPublicCircles());
    }
}
