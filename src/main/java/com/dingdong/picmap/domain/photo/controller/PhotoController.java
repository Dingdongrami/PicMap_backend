package com.dingdong.picmap.domain.photo.controller;

import com.dingdong.picmap.domain.photo.dto.PhotoResponseDto;
import com.dingdong.picmap.domain.photo.dto.PhotoUploadRequestDto;
import com.dingdong.picmap.domain.photo.service.PhotoService;
import com.dingdong.picmap.domain.photo.service.PhotoUploadService;
import com.dingdong.picmap.domain.photo.service.S3Uploader;
import com.dingdong.picmap.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/photos")
public class PhotoController {

    private final PhotoUploadService photoUploadService;
    private final PhotoService photoService;
    private final UserService userService;
    private final S3Uploader s3Uploader;

    // 사진 업로드
    @ResponseBody
    @PostMapping(value = "/upload")
    public ResponseEntity<List<PhotoResponseDto>> uploadPhoto(@RequestParam(value="image") List<MultipartFile> files, @RequestBody PhotoUploadRequestDto requestDto) throws Exception {
        return ResponseEntity.ok(photoUploadService.uploadPhoto(files, requestDto));
    }

    // photo id 로 사진 조회
    @GetMapping("/")
    public ResponseEntity<PhotoResponseDto> getPhoto(@RequestParam Long photoId) {
        return ResponseEntity.ok(photoService.getPhotoByPhotoId(photoId));
    }

    // user id 로 사진 리스트 조회
    @GetMapping("/get/{userId}")
    public ResponseEntity<List<PhotoResponseDto>> getPhotoByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(photoService.getPhotosByUserId(userId));
    }

    // circle id 로 사진 리스트 조회
    @GetMapping("/get/circle")
    public ResponseEntity<List<PhotoResponseDto>> getPhotoByCircleId(@RequestParam Long circleId) {
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
}
