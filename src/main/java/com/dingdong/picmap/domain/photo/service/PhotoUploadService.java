package com.dingdong.picmap.domain.photo.service;

import com.dingdong.picmap.domain.circle.entity.Circle;
import com.dingdong.picmap.domain.circle.repository.CircleRepository;
import com.dingdong.picmap.domain.circle.repository.CircleUserRepository;
import com.dingdong.picmap.domain.photo.dto.PhotoResponseDto;
import com.dingdong.picmap.domain.photo.dto.PhotoUploadRequestDto;
import com.dingdong.picmap.domain.photo.entity.Photo;
import com.dingdong.picmap.domain.photo.mapper.PhotoEntityMapper;
import com.dingdong.picmap.domain.photo.repository.PhotoUploadRepository;
import com.dingdong.picmap.domain.sharedAlbum.mapper.CircleSharedAlbumMapper;
import com.dingdong.picmap.domain.sharedAlbum.repository.CircleSharedAlbumRepository;
import com.dingdong.picmap.domain.user.entity.User;
import com.dingdong.picmap.domain.user.repository.UserRepository;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import com.drew.metadata.exif.GpsDirectory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;


@Slf4j
@Service
@RequiredArgsConstructor
public class PhotoUploadService {

    private final PhotoUploadRepository photoUploadRepository;
    private final UserRepository userRepository;
    private final CircleRepository circleRepository;
    private final CircleUserRepository circleUserRepository;
    private final CircleSharedAlbumRepository circleSharedAlbumRepository;
    private final PhotoMetadataService photoMetadataService;
    private final PhotoEntityMapper photoEntityMapper;
    private final CircleSharedAlbumMapper circleSharedAlbumMapper;

    @Autowired
    private S3Uploader s3Uploader;

    @Transactional(rollbackFor = Exception.class)
    public List<PhotoResponseDto> uploadPhoto(List<MultipartFile> images, PhotoUploadRequestDto requestDto) {
        if (images.isEmpty()) {
            throw new IllegalArgumentException("이미지가 없습니다.");
        }
        List<Photo> photoList = new ArrayList<>();
        try {
            User user = userRepository.findById(requestDto.getUserId())
                    .orElseThrow(() -> new EntityNotFoundException("해당 유저가 없습니다. id=" + requestDto.getUserId()));
            Circle circle = circleRepository.findById(requestDto.getCircleId())
                    .orElseThrow(() -> new EntityNotFoundException("해당 써클이 없습니다. id=" + requestDto.getCircleId()));

            images.forEach(image -> {
                String fileName = s3Uploader.upload(image, "images");
                Photo photo = PhotoEntityMapper.toPhotoEntity(user, requestDto);
                photo.setFilePath(fileName);
                Photo savedPhoto = photoUploadRepository.save(photo);
                photoList.add(savedPhoto);
                circleSharedAlbumRepository.save(circleSharedAlbumMapper.createCircleSharedAlbum(savedPhoto, circle, user));
            });
        } catch (Exception e) {
            log.error("사진 업로드 실패", e);
            throw e;
        }

        return PhotoResponseDto.listOf(photoList);
    }

    private File convert(MultipartFile image) throws IOException {
        File file = new File(Objects.requireNonNull(image.getOriginalFilename()));
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(image.getBytes());
        } catch (IOException e) {
            log.error("파일 변환 실패", e);
            throw e;
        }
        return file;
    }

    private Photo setMetadata(Photo photo, MultipartFile multipartFile) throws ImageProcessingException, IOException {
        File file = convert(multipartFile);
        Map<String, Directory> metadata = photoMetadataService.getMetadata(file);

        double latitude = 0;
        double longitude = 0;
        LocalDateTime shootingDate = null;

        if (metadata != null) {
            GpsDirectory gps = (GpsDirectory) metadata.get("gps");
            ExifSubIFDDirectory exif = (ExifSubIFDDirectory) metadata.get("exif");

            latitude = gps.getGeoLocation().getLatitude();
            longitude = gps.getGeoLocation().getLongitude();
            Date exifDate = exif.getDate(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL);
            shootingDate = LocalDateTime.ofInstant(exifDate.toInstant(), ZoneId.systemDefault());
            photo.setMetaData(latitude, longitude, shootingDate);
        }
        return photo;
    }
}
