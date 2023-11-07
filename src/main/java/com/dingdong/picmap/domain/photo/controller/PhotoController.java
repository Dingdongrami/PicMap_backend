package com.dingdong.picmap.domain.photo.controller;

import com.dingdong.picmap.domain.photo.entity.Photo;
import com.dingdong.picmap.domain.photo.service.PhotoUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class PhotoController {

    private final PhotoUploadService photoUploadService;

    // 사진 업로드
    @ResponseBody
    @PostMapping(value = "/api/photo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Long uploadPhoto(HttpServletRequest request, @RequestBody MultipartFile file, Photo photo) throws Exception {
        return photoUploadService.uploadPhoto(file, photo);
    }
}
