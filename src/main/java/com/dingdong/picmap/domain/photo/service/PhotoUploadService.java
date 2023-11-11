package com.dingdong.picmap.domain.photo.service;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.dingdong.picmap.domain.photo.entity.Photo;
import com.dingdong.picmap.domain.photo.repository.PhotoUploadRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class PhotoUploadService {

    private final PhotoUploadRepository photoUploadRepository;

    @Autowired
    private S3Uploader s3Uploader;

    @Transactional
    public Long uploadPhoto(MultipartFile image, Photo requestPhoto) throws IOException {
        log.info("service - uploadPhoto ; image: {}", image);
        // image null check
        if (image == null) {
            throw new IllegalArgumentException("image is null");
        }

        String storeFileName = s3Uploader.upload(image, "images");
        requestPhoto.setFilePath(storeFileName);
        // s3 upload 한 후에 해당 사진의 metadata 에서 longitude, latitude 추출


        Photo savedPhoto = photoUploadRepository.save(requestPhoto);
        return savedPhoto.getId();
    }

    public Photo getPhoto(Long id) {
        return photoUploadRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 사진이 없습니다."));
    }
}
