package com.dingdong.picmap.domain.photo.controller;

import com.dingdong.picmap.domain.photo.entity.Photo;
import com.dingdong.picmap.domain.photo.service.PhotoUploadService;
import com.dingdong.picmap.domain.photo.service.S3Uploader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/photo")
public class PhotoController {

    private final PhotoUploadService photoUploadService;
    private final S3Uploader s3Uploader;

    // 사진 업로드
    @ResponseBody
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Long uploadPhoto(HttpServletRequest request, @RequestParam(value="image") MultipartFile file) throws Exception {
        log.info("request: {}", request);
        log.info("file: {}", file.getOriginalFilename());
        Photo photo = new Photo();
        return photoUploadService.uploadPhoto(file, photo);
    }

    // photo id 로 사진 조회
    @GetMapping("/")
    public Photo getPhoto(@RequestParam(value = "id") Long id) {
        return photoUploadService.getPhoto(id);
    }

}
