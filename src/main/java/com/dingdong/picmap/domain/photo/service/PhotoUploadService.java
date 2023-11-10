package com.dingdong.picmap.domain.photo.service;

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

        String storeFileName = s3Uploader.upload(image, "requestPhoto");
        requestPhoto.setFilePath(storeFileName);
        Photo savedPhoto = photoUploadRepository.save(requestPhoto);
        return savedPhoto.getId();
    }
}
