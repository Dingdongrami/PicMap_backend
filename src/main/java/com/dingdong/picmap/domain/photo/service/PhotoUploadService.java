package com.dingdong.picmap.domain.photo.service;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.dingdong.picmap.domain.photo.entity.Photo;
import com.dingdong.picmap.domain.photo.repository.PhotoUploadRepository;
import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import com.drew.metadata.exif.GpsDirectory;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;


@Slf4j
@Service
@RequiredArgsConstructor
public class PhotoUploadService {

    private final PhotoUploadRepository photoUploadRepository;

    @Autowired
    private S3Uploader s3Uploader;

    @SneakyThrows
    @Transactional
    public Long uploadPhoto(MultipartFile image, Photo requestPhoto) throws IOException {
        log.info("service - uploadPhoto ; image: {}", image);
        // image null check
        if (image == null) {
            throw new IllegalArgumentException("image is null");
        }

        File file = s3Uploader.convert(image)
                .orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File 전환 실패"));
        String storeFileName = s3Uploader.upload(file, "images");

        Map<String, Directory> readMetadata = s3Uploader.readMetadata(file);
        double latitude = 0;
        double longitude = 0;
        // 임시 날짜
        Date shootingDate = new Date();

        if (readMetadata != null) {
            GpsDirectory gps = (GpsDirectory) readMetadata.get("gps");
            ExifSubIFDDirectory exif = (ExifSubIFDDirectory) readMetadata.get("exif");

            latitude = gps.getGeoLocation().getLatitude();
            longitude = gps.getGeoLocation().getLongitude();
            shootingDate = exif.getDateOriginal();
        }

        log.info("readMetadata finished");
        requestPhoto.setFilePath(storeFileName);
        requestPhoto.setShootingDate(shootingDate);
        requestPhoto.setLatitude(latitude);
        requestPhoto.setLongitude(longitude);

        Photo savedPhoto = photoUploadRepository.save(requestPhoto);
        s3Uploader.readMetadata(file);
        return savedPhoto.getId();
    }

    public Photo getPhoto(Long id) {
        return photoUploadRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 사진이 없습니다."));
    }
}
