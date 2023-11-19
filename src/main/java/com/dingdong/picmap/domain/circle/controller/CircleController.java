package com.dingdong.picmap.domain.circle.controller;

import com.dingdong.picmap.domain.circle.dto.CircleCreateRequestDto;
import com.dingdong.picmap.domain.circle.dto.CircleCreateResponseDto;
import com.dingdong.picmap.domain.circle.service.CircleCreateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/circles")
@RequiredArgsConstructor
public class CircleController {

    private final CircleCreateService circleCreateService;

    // 써클 생성
    @PostMapping("/add-circle")
    public ResponseEntity<CircleCreateResponseDto> createCircle(@RequestBody CircleCreateRequestDto request) {
        return ResponseEntity.ok(circleCreateService.createCircle(request));
    }

}
