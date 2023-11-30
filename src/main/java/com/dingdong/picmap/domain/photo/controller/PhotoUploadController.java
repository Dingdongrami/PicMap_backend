package com.dingdong.picmap.domain.photo.controller;

import com.dingdong.picmap.domain.photo.dto.PhotoResponseDto;
import com.dingdong.picmap.domain.photo.dto.PhotoUploadRequestDto;
import com.dingdong.picmap.domain.photo.service.PhotoDownloadService;
import com.dingdong.picmap.domain.photo.service.PhotoUploadService;
import com.dingdong.picmap.domain.photo.service.S3Uploader;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/photos")
public class PhotoUploadController {

    private final PhotoUploadService photoUploadService;
    private final PhotoDownloadService photoDownloadService;
    private final ObjectMapper objectMapper;
    private final S3Uploader s3Uploader;

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
        try {
            PhotoUploadRequestDto requestDto = objectMapper.readValue(jsonData, PhotoUploadRequestDto.class);
            return ResponseEntity.ok(photoUploadService.uploadPhoto(images, requestDto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // 사진 다운로드
    @PostMapping(value = "/download", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> downloadPhoto(@RequestParam String filePath) throws Exception {
        return photoDownloadService.download(filePath);
    }
}
