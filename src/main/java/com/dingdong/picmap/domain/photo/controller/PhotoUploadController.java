package com.dingdong.picmap.domain.photo.controller;

import com.dingdong.picmap.domain.photo.dto.PhotoRequestDto;
import com.dingdong.picmap.domain.photo.dto.PhotoResponseDto;
import com.dingdong.picmap.domain.photo.dto.PhotoUploadRequestDto;
import com.dingdong.picmap.domain.photo.service.PhotoDownloadService;
import com.dingdong.picmap.domain.photo.service.PhotoUploadService;
import com.dingdong.picmap.domain.photo.service.s3.S3Uploader;
import com.fasterxml.jackson.core.type.TypeReference;
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
public class PhotoUploadController {

    private final PhotoUploadService photoUploadService;
    private final PhotoDownloadService photoDownloadService;
    private final ObjectMapper objectMapper;
    private final S3Uploader s3Uploader;

    // 사진 업로드
    @ResponseBody
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<PhotoResponseDto>> uploadPhoto(HttpServletRequest httpServletRequest) throws Exception {
        if (!(httpServletRequest instanceof MultipartHttpServletRequest)) {
            throw new IllegalArgumentException("MultipartHttpServletRequest is not valid");
        }

        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) httpServletRequest;
        String[] photoInfoList = multipartHttpServletRequest.getParameterValues("photoInfoList");
        String request = multipartHttpServletRequest.getParameter("jsonData");
        List<MultipartFile> files = multipartHttpServletRequest.getFiles("images");

        PhotoRequestDto photoRequestDto = objectMapper.readValue(request, PhotoRequestDto.class);
        List<PhotoUploadRequestDto> requestDtoList = objectMapper.readValue(photoInfoList[0], new TypeReference<>() {});

        return ResponseEntity.ok(photoUploadService.uploadPhoto(requestDtoList, photoRequestDto, files));
    }

    // 사진 다운로드
    @PostMapping(value = "/download", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> downloadPhoto(@RequestParam String filePath) throws Exception {
        return photoDownloadService.download(filePath);
    }
}
