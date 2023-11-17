package com.dingdong.picmap.domain.photo.service;

import com.dingdong.picmap.domain.photo.entity.Photo;
import com.dingdong.picmap.domain.photo.repository.PhotoUploadRepository;
import com.dingdong.picmap.domain.user.entity.User;
import com.dingdong.picmap.domain.user.repository.UserRepository;
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

import javax.persistence.EntityNotFoundException;
import java.io.File;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;


@Slf4j
@Service
@RequiredArgsConstructor
public class PhotoUploadService {

    private final PhotoUploadRepository photoUploadRepository;
    private final UserRepository userRepository;

    @Autowired
    private S3Uploader s3Uploader;

    @SneakyThrows
    @Transactional
    public Long uploadPhoto(MultipartFile image, Photo requestPhoto, Long userId) {
        if (image == null) {
            throw new IllegalArgumentException("image is null");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("해당 유저가 없습니다. id=" + userId));

        File file = s3Uploader.convert(image)
                .orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File 전환 실패"));
        String storeFileName = s3Uploader.upload(file, "images");

        Map<String, Directory> readMetadata = s3Uploader.readMetadata(file);
        // 초기값 : 서울역
        double latitude = 37.55468153819696;
        double longitude = 126.97059220394807;

        // 초기값 : 업로드 된 시간
        LocalDateTime shootingDate = LocalDateTime.now();

        if (readMetadata != null) {
            GpsDirectory gps = (GpsDirectory) readMetadata.get("gps");
            ExifSubIFDDirectory exif = (ExifSubIFDDirectory) readMetadata.get("exif");

            latitude = gps.getGeoLocation().getLatitude();
            longitude = gps.getGeoLocation().getLongitude();
            Date exifDate = exif.getDate(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL);
            shootingDate = LocalDateTime.ofInstant(exifDate.toInstant(), ZoneId.systemDefault());
        }

        log.info("readMetadata finished");
        requestPhoto.setFilePath(storeFileName);
        requestPhoto.setMetaData(latitude, longitude, shootingDate);
        requestPhoto.setUser(user);

        Photo savedPhoto = photoUploadRepository.save(requestPhoto);
        s3Uploader.readMetadata(file);
        return savedPhoto.getId();
    }
}
