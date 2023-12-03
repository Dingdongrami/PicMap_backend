package com.dingdong.picmap.domain.circle.controller;

import com.dingdong.picmap.domain.circle.dto.request.CircleCreateRequestDto;
import com.dingdong.picmap.domain.circle.dto.request.CircleRequestDto;
import com.dingdong.picmap.domain.circle.dto.response.CircleResponseDto;
import com.dingdong.picmap.domain.circle.dto.response.CircleUserResponseDto;
import com.dingdong.picmap.domain.circle.service.CircleCreateService;
import com.dingdong.picmap.domain.circle.service.CircleService;
import com.dingdong.picmap.domain.global.BaseTimeEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/circles")
public class CircleController extends BaseTimeEntity {

    private final CircleCreateService circleCreateService;
    private final CircleService circleService;
    private final ObjectMapper objectMapper;

    // 써클 생성
    @ResponseBody
    @PostMapping("/add-circle")
    public ResponseEntity<CircleResponseDto> createCircle(HttpServletRequest httpServletRequest) throws IOException {

        if (!(httpServletRequest instanceof MultipartHttpServletRequest)) {
            throw new IllegalArgumentException("MultipartHttpServletRequest is not valid");
        }

        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) httpServletRequest;
        MultipartFile thumbnail = multipartHttpServletRequest.getFile("thumbnail");
        String jsonData = multipartHttpServletRequest.getParameter("jsonData");

        CircleCreateRequestDto requestDto = objectMapper.readValue(jsonData, CircleCreateRequestDto.class);
        return ResponseEntity.ok(circleCreateService.createCircle(requestDto, thumbnail));
    }

    // 써클 thumbnail 수정
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

    // 써클 수정
    @PutMapping("/update/{circleId}")
    public ResponseEntity<CircleResponseDto> updateCircle(@PathVariable Long circleId, @RequestBody String name) {
        return ResponseEntity.ok(circleService.updateCircleName(circleId, name));
    }

    // 써클 멤버 조회
    @GetMapping("/{circleId}/members")
    public ResponseEntity<CircleUserResponseDto> getCircleMembers(@PathVariable Long circleId) {
        return ResponseEntity.ok(circleService.getCircleMembers(circleId));
    }
}
