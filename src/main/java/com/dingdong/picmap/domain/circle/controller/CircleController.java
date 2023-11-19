package com.dingdong.picmap.domain.circle.controller;

import com.dingdong.picmap.domain.circle.dto.CircleCreateRequestDto;
import com.dingdong.picmap.domain.circle.dto.CircleCreateResponseDto;
import com.dingdong.picmap.domain.circle.service.CircleCreateService;
import com.dingdong.picmap.domain.global.BaseTimeEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/circle")
public class CircleController extends BaseTimeEntity {

    private final CircleCreateService circleCreateService;

    // 써클 생성
    @PostMapping("/add-circle")
    public ResponseEntity<CircleCreateResponseDto> createCircle(@RequestBody CircleCreateRequestDto request) {
        return ResponseEntity.ok(circleCreateService.createCircle(request));
    }

}
