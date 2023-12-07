package com.dingdong.picmap.domain.circle.controller;

import com.dingdong.picmap.domain.circle.dto.request.CircleJoinRequestDto;
import com.dingdong.picmap.domain.circle.dto.request.CircleLeaveRequestDto;
import com.dingdong.picmap.domain.circle.dto.response.CircleResponseDto;
import com.dingdong.picmap.domain.circle.service.CircleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/circles")
public class PublicCircleController {

    private final CircleService circleService;

    // public & government 한 써클 리스트 조회
    @GetMapping("/public")
    public ResponseEntity<List<CircleResponseDto>> getPublicCircles() {
        return ResponseEntity.ok(circleService.getPublicCircles());
    }

    // GOVERNMENT 써클 리스트 조회
    @GetMapping("/government")
    public ResponseEntity<List<CircleResponseDto>> getGovernmentCircles() {
        return ResponseEntity.ok(circleService.getGovernmentCircles());
    }

    @PostMapping("/join")
    public ResponseEntity<?> joinCircle(@RequestBody CircleJoinRequestDto requestDto) {
        circleService.joinCircle(requestDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/leave")
    public ResponseEntity<String> leaveCircle(@RequestBody CircleLeaveRequestDto requestDto) {
        circleService.leaveCircle(requestDto);
        return ResponseEntity.ok("success");
    }

}
