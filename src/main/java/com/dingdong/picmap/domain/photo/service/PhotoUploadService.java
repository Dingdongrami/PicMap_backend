package com.dingdong.picmap.domain.photo.service;

import com.dingdong.picmap.config.util.MeasureExecutionTime;
import com.dingdong.picmap.domain.circle.entity.Circle;
import com.dingdong.picmap.domain.circle.repository.CircleRepository;
import com.dingdong.picmap.domain.circle.repository.CircleUserRepository;
import com.dingdong.picmap.domain.photo.dto.PhotoRequestDto;
import com.dingdong.picmap.domain.photo.dto.PhotoResponseDto;
import com.dingdong.picmap.domain.photo.dto.PhotoUploadRequestDto;
import com.dingdong.picmap.domain.photo.entity.Photo;
import com.dingdong.picmap.domain.photo.mapper.PhotoEntityMapper;
import com.dingdong.picmap.domain.photo.repository.PhotoUploadRepository;
import com.dingdong.picmap.domain.photo.service.s3.S3Uploader;
import com.dingdong.picmap.domain.sharedAlbum.mapper.CircleSharedAlbumMapper;
import com.dingdong.picmap.domain.sharedAlbum.repository.CircleSharedAlbumRepository;
import com.dingdong.picmap.domain.user.entity.User;
import com.dingdong.picmap.domain.user.repository.UserRepository;
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
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

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

    @MeasureExecutionTime
    @Transactional(rollbackFor = Exception.class)
    public List<PhotoResponseDto> uploadPhoto(List<PhotoUploadRequestDto> requestDtoList, PhotoRequestDto photoRequestDto, List<MultipartFile> images) {
        if (images.isEmpty()) {
            throw new IllegalArgumentException("이미지가 없습니다.");
        }
        User user = userRepository.findById(photoRequestDto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다."));
        Circle circle = circleRepository.findById(photoRequestDto.getCircleId())
                .orElseThrow(() -> new EntityNotFoundException("써클을 찾을 수 없습니다."));

        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        log.info("thread pool size: {}", Runtime.getRuntime().availableProcessors());
        try {
            List<CompletableFuture<Photo>> futures = images.stream()
                    .map(image -> CompletableFuture.supplyAsync(() ->
                        uploadAndSavePhoto(image, user, requestDtoList, circle), executorService))
                    .collect(Collectors.toList());

            CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
            allOf.get();    // 모든 작업이 끝날 때까지 대기

            List<Photo> savedPhotoList = futures.stream()
                    .map(CompletableFuture::join)
                    .collect(Collectors.toList());

            savedPhotoList.forEach(photo -> {
                circleSharedAlbumRepository.save(circleSharedAlbumMapper.toCircleSharedAlbumEntity(photo, circle, user));
            });
            return PhotoResponseDto.listOf(savedPhotoList);
        } catch (ExecutionException | InterruptedException e) {
            log.error("사진 업로드 실패: {}", e.getMessage());
            throw new RuntimeException(e);
        } finally {
            executorService.shutdown();
        }
    }

    private Photo uploadAndSavePhoto(MultipartFile image, User user, List<PhotoUploadRequestDto> requestDtoList, Circle circle) {
        String filePath = s3Uploader.upload(image, "images");
        Photo photo = photoEntityMapper.toPhotoEntity(user, requestDtoList.get(0));
        photo.setFilePath(filePath);
        return photoUploadRepository.save(photo);
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
}
