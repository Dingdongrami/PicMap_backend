package com.dingdong.picmap.domain.photo.controller;

import com.dingdong.picmap.domain.photo.dto.PhotoResponseDto;
import com.dingdong.picmap.domain.photo.dto.PhotoUploadRequestDto;
import com.dingdong.picmap.domain.photo.service.PhotoService;
import com.dingdong.picmap.domain.photo.service.PhotoUploadService;
import com.dingdong.picmap.domain.photo.service.S3Uploader;
import com.dingdong.picmap.domain.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
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
    private final ObjectMapper objectMapper;

    // 사진 업로드
    @ResponseBody
    @PostMapping(value = "/upload")
    public ResponseEntity<List<PhotoResponseDto>> uploadPhoto(HttpServletRequest httpServletRequest) throws Exception {
        if (!(httpServletRequest instanceof MultipartHttpServletRequest)) {
            throw new IllegalArgumentException("MultipartHttpServletRequest is not valid");
        }

        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) httpServletRequest;
        List<MultipartFile> images = multipartHttpServletRequest.getFiles("image");
        String jsonData = multipartHttpServletRequest.getParameter("jsonData");

        PhotoUploadRequestDto requestDto = objectMapper.readValue(jsonData, PhotoUploadRequestDto.class);
        return ResponseEntity.ok(photoUploadService.uploadPhoto(images, requestDto));
    }

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
}
