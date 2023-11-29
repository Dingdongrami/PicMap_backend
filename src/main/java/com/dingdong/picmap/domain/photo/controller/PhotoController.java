package com.dingdong.picmap.domain.photo.controller;

import com.dingdong.picmap.domain.photo.dto.CameraPhotoUploadRequestDto;
import com.dingdong.picmap.domain.photo.dto.PhotoLocationResponseDto;
import com.dingdong.picmap.domain.photo.dto.PhotoResponseDto;
import com.dingdong.picmap.domain.photo.service.PhotoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/photos")
public class PhotoController {

    private final PhotoService photoService;

    // photo id 로 사진 조회
    @GetMapping("/")
    public ResponseEntity<PhotoResponseDto> getPhoto(@RequestParam Long photoId) {
        return ResponseEntity.ok(photoService.getPhotoByPhotoId(photoId));
    }

    // user id 로 사진 리스트 조회
    @GetMapping("/get/user/{userId}")
    public ResponseEntity<List<PhotoResponseDto>> getPhotoByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(photoService.getPhotosByUserId(userId));
    }

    // circle id 로 사진 리스트 조회
    @GetMapping("/get/circle/{circleId}")
    public ResponseEntity<List<PhotoResponseDto>> getPhotoByCircleId(@PathVariable Long circleId) {
        return ResponseEntity.ok(photoService.getPhotosByCircleId(circleId));
    }

    @DeleteMapping("/{photoId}")
    public ResponseEntity<String> deletePhoto(@PathVariable Long photoId) {
        try {
            return ResponseEntity.ok(photoService.deletePhoto(photoId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("{photoId}/location")
    public ResponseEntity<PhotoLocationResponseDto> getLocation(@PathVariable Long photoId) {
        return ResponseEntity.ok(photoService.getLocation(photoId));
    }
}
