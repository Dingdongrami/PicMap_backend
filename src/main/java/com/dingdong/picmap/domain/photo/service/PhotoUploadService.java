package com.dingdong.picmap.domain.photo.service;

import com.dingdong.picmap.domain.circle.entity.Circle;
import com.dingdong.picmap.domain.circle.repository.CircleRepository;
import com.dingdong.picmap.domain.photo.dto.PhotoResponseDto;
import com.dingdong.picmap.domain.photo.dto.PhotoUploadRequestDto;
import com.dingdong.picmap.domain.photo.entity.Photo;
import com.dingdong.picmap.domain.photo.repository.PhotoUploadRepository;
import com.dingdong.picmap.domain.sharedAlbum.mapper.CircleSharedAlbumMapper;
import com.dingdong.picmap.domain.sharedAlbum.repository.CircleSharedAlbumRepository;
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
    private PhotoMetadataService photoMetadataService;
    private CircleSharedAlbumRepository circleSharedAlbumRepository;
    private CircleSharedAlbumMapper circleSharedAlbumMapper;

    @Autowired
    private S3Uploader s3Uploader;

    @SneakyThrows
    @Transactional
    public List<PhotoResponseDto> uploadPhoto(List<MultipartFile> images, PhotoUploadRequestDto requestDto) {
        if (images.isEmpty()) {
            throw new IllegalArgumentException("이미지가 없습니다.");
        }

        User user = userRepository.findById(requestDto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("해당 유저가 없습니다. id=" + requestDto.getUserId()));
        Circle circle = circleRepository.findById(requestDto.getCircleId())
                .orElseThrow(() -> new EntityNotFoundException("해당 써클이 없습니다. id=" + requestDto.getCircleId()));

        List<Photo> photoList = new ArrayList<>();
        images.forEach(image -> {
            String fileName = s3Uploader.upload(image);
            Photo setMetadataPhoto = setMetadata(new Photo(), image);
            setMetadataPhoto.setFilePath(fileName);
            setMetadataPhoto.setUser(user);

            Photo savedPhoto = photoUploadRepository.save(setMetadataPhoto);
            photoList.add(savedPhoto);

            circleSharedAlbumRepository.save(circleSharedAlbumMapper.createCircleSharedAlbum(savedPhoto, circle, user));
        });
        return PhotoResponseDto.listOf(photoList);
    }

    private File convert(MultipartFile image) {
        File file = new File(Objects.requireNonNull(image.getOriginalFilename()));
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(image.getBytes());
        } catch (IOException e) {
            log.error("파일 변환 실패", e);
        }
        return file;
    }

    @SneakyThrows
    private Photo setMetadata(Photo photo, MultipartFile image) {
        File file = convert(image);
        Map<String, Directory> metadata = photoMetadataService.getMetadata(file);

        // 초기값 : 서울역
        double latitude = 37.55468153819696;
        double longitude = 126.97059220394807;

        // 초기값 : 업로드 된 시간
        LocalDateTime shootingDate = LocalDateTime.now();

        photo.setMetaData(latitude, longitude, shootingDate);

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
