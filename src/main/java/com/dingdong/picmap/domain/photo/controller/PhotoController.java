package com.dingdong.picmap.domain.photo.controller;

import com.dingdong.picmap.domain.photo.dto.PhotoResponse;
import com.dingdong.picmap.domain.photo.entity.Photo;
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

    // 사진 업로드
    @ResponseBody
    @PostMapping(value = "/{userId}/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Long uploadPhoto(HttpServletRequest request, @RequestParam(value="image") MultipartFile file, @PathVariable Long userId) throws Exception {
        log.info("request: {}", request);
        log.info("file: {}", file.getOriginalFilename());
        Photo photo = new Photo();
        return photoUploadService.uploadPhoto(file, photo, userId);
    }

    // photo id 로 사진 조회
    @GetMapping("/")
    public ResponseEntity<PhotoResponse> getPhoto(@RequestParam Long photoId) {
        return ResponseEntity.ok(photoService.getPhotoByPhotoId(photoId));
    }

    // user id 로 사진 리스트 조회
    @GetMapping("/get/{userId}")
    public ResponseEntity<List<PhotoResponse>> getPhotoByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(photoService.getPhotoByUserId(userId));
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
