package com.dingdong.picmap.domain.photo.controller;

import com.dingdong.picmap.domain.photo.dto.PhotoLocationResponseDto;
import com.dingdong.picmap.domain.photo.dto.PhotoResponseDto;
import com.dingdong.picmap.domain.photo.dto.PhotoSortRequestDto;
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

    // user id 로 사진 리스트 조회(유저가 올린 사진)
    @GetMapping("/get/user/{userId}")
    public ResponseEntity<List<PhotoResponseDto>> getPhotoListByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(photoService.getPhotosByUserId(userId));
    }

    // circle id 로 사진 리스트 조회
    @GetMapping("/get/circle/{circleId}")
    public ResponseEntity<List<PhotoResponseDto>> getPhotoListByCircleId(@PathVariable Long circleId) {
        return ResponseEntity.ok(photoService.getPhotosByCircleId(circleId));
    }

    // userId 가 속한 PUBLIC 써클의 사진 리스트 조회
    @GetMapping("/get/public/{userId}")
    public ResponseEntity<List<PhotoResponseDto>> getPhotoListByPublicCircleByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(photoService.getPhotosByPublicCircleByUserId(userId));
    }

    // userId 가 속한 전체 써클의 사진 리스트 조회
    @GetMapping("/get/all-circle/{userId}")
    public ResponseEntity<List<PhotoResponseDto>> getPhotoListByAllCirclesByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(photoService.getPhotosByAllCirclesByUserId(userId));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deletePhoto(@RequestBody List<Long> photoIdList) {
        try {
            return ResponseEntity.ok(photoService.deletePhoto(photoIdList));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{photoId}/location")
    public ResponseEntity<PhotoLocationResponseDto> getLocation(@PathVariable Long photoId) {
        return ResponseEntity.ok(photoService.getLocation(photoId));
    }

    // 사진 정렬 - 최신 순(latest), 오래된 순(oldest), 좋아요 많은 순(like)
    @PostMapping("/sort")
    public ResponseEntity<List<PhotoResponseDto>> getPhotoListBySort(@RequestBody PhotoSortRequestDto requestDto) {
        return ResponseEntity.ok(photoService.getPhotosBySort(requestDto));
    }

    // 써클의 최신 사진 4장 조회
    @GetMapping("/latest-four")
    public ResponseEntity<List<PhotoResponseDto>> getLatestPhotosByCircleId(@RequestParam Long circleId) {
        return ResponseEntity.ok(photoService.getLatestPhotosByCircleId(circleId));
    }

}
