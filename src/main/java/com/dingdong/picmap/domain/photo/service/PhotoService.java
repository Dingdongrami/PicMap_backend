package com.dingdong.picmap.domain.photo.service;

import com.dingdong.picmap.config.util.MeasureExecutionTime;
import com.dingdong.picmap.domain.circle.entity.Circle;
import com.dingdong.picmap.domain.circle.repository.CircleRepository;
import com.dingdong.picmap.domain.circle.repository.CircleUserRepository;
import com.dingdong.picmap.domain.global.BaseTimeEntity;
import com.dingdong.picmap.domain.photo.dto.PhotoLocationResponseDto;
import com.dingdong.picmap.domain.photo.dto.PhotoResponseDto;
import com.dingdong.picmap.domain.photo.dto.PhotoSortRequestDto;
import com.dingdong.picmap.domain.photo.entity.Photo;
import com.dingdong.picmap.domain.photo.repository.PhotoRepository;
import com.dingdong.picmap.domain.photo.repository.PhotoUploadRepository;
import com.dingdong.picmap.domain.sharedAlbum.repository.CircleSharedAlbumRepository;
import com.dingdong.picmap.domain.user.entity.User;
import com.dingdong.picmap.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PhotoService {

    private final PhotoUploadRepository photoUploadRepository;
    private final PhotoRepository photoRepository;
    private final UserRepository userRepository;
    private final CircleRepository circleRepository;
    private final CircleSharedAlbumRepository circleSharedAlbumRepository;
    private final CircleUserRepository circleUserRepository;

    public PhotoResponseDto getPhotoByPhotoId(Long id) {
        Photo findPhoto = photoUploadRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("해당 사진이 없습니다."));
        return PhotoResponseDto.of(findPhoto);
    }

    @MeasureExecutionTime
    public List<PhotoResponseDto> getPhotosByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("해당 유저가 없습니다."));
        try {
            ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
            CompletableFuture<List<Photo>> photoListFuture = CompletableFuture.supplyAsync(() -> photoUploadRepository.findAllByUser(user), executorService);
            CompletableFuture<Void> allOf = CompletableFuture.allOf(photoListFuture);
            allOf.get();

            List<PhotoResponseDto> photoResponseDtoList = photoListFuture.get().stream()
                    .map(PhotoResponseDto::of)
                    .collect(Collectors.toList());
            executorService.shutdown();
            return photoResponseDtoList;
        } catch (InterruptedException | ExecutionException e) {
            log.error("error: {}", e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @MeasureExecutionTime
    public List<PhotoResponseDto> getPhotosByPublicCircleByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("해당 유저가 없습니다."));
        List<Circle> publicCircles = circleUserRepository.findCirclesByUser(user)
                .stream()
                .filter(circle -> circle.getIsPublic() || circle.getIsGovernment())
                .collect(Collectors.toList());
        try {
            ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
            List<CompletableFuture<List<Photo>>> futures = publicCircles.stream()
                    .map(circle -> CompletableFuture.supplyAsync(() -> photoUploadRepository.findAllByCircle(circle), executorService))
                    .collect(Collectors.toList());
            CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
            allOf.get();

            List<PhotoResponseDto> photoResponseDtoList = futures.stream()
                    .map(CompletableFuture::join)
                    .flatMap(List::stream)
                    .map(PhotoResponseDto::of)
                    .collect(Collectors.toList());
            executorService.shutdown();
            return photoResponseDtoList;
        } catch (InterruptedException | ExecutionException e) {
            log.error("error: {}", e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public List<PhotoResponseDto> getPhotosByAllCirclesByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("해당 유저가 없습니다."));
        List<Circle> allCircles = circleUserRepository.findCirclesByUser(user);

        try {
            ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
            List<CompletableFuture<List<Photo>>> futures = allCircles.stream()
                    .map(circle -> CompletableFuture.supplyAsync(() -> photoUploadRepository.findAllByCircle(circle), executorService))
                    .collect(Collectors.toList());
            CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
            allOf.get();

            List<PhotoResponseDto> photoResponseDtoList = futures.stream()
                    .map(CompletableFuture::join)
                    .flatMap(List::stream)
                    .map(PhotoResponseDto::of)
                    .collect(Collectors.toList());
            executorService.shutdown();
            return photoResponseDtoList;
        } catch (InterruptedException | ExecutionException e) {
            log.error("error: {}", e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public List<PhotoResponseDto> getPhotosByPublicAndGovCircles() {
        List<Circle> circleList = circleRepository.findByStatus("PUBLIC");
        circleList.addAll(circleRepository.findByStatus("GOVERNMENT"));

        List<Photo> photoList = circleList.stream()
                .map(photoUploadRepository::findAllByCircle)
                .flatMap(List::stream)
                .collect(Collectors.toList());
        log.info("photoList: {}", photoList.size());

        return PhotoResponseDto.listOf(photoList);
    }

    @Transactional
    public String deletePhoto(List<Long> photoIdList) {
        photoIdList.stream()
                .map(photoId -> photoRepository.findById(photoId)
                        .orElseThrow(() -> new EntityNotFoundException("해당 사진이 없습니다.")))
                .forEach(photo -> {
                    circleSharedAlbumRepository.deleteByPhoto(photo);
                    photoRepository.delete(photo);
                });
        return "success";
    }

    @MeasureExecutionTime
    public List<PhotoResponseDto> getPhotosByCircleId(Long circleId) {
        Circle circle = circleRepository.findById(circleId)
                .orElseThrow(() -> new EntityNotFoundException("해당 써클이 없습니다."));
        try {
            ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
            CompletableFuture<List<Photo>> photoListFuture = CompletableFuture.supplyAsync(() -> photoUploadRepository.findAllByCircle(circle), executorService);
            CompletableFuture<Void> allOf = CompletableFuture.allOf(photoListFuture);
            allOf.get();

            List<PhotoResponseDto> photoResponseDtoList = photoListFuture.get().stream()
                    .map(PhotoResponseDto::of)
                    .collect(Collectors.toList());
            executorService.shutdown();
            return photoResponseDtoList;
        } catch (InterruptedException | ExecutionException e) {
            log.error("error: {}", e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public PhotoLocationResponseDto getLocation(Long photoId) {
        Photo photo = photoRepository.findById(photoId)
                .orElseThrow(() -> new EntityNotFoundException("해당 사진이 없습니다."));
        return PhotoLocationResponseDto.builder()
                .id(photo.getId())
                .filePath(photo.getFilePath())
                .latitude(photo.getLatitude())
                .longitude(photo.getLongitude())
                .build();
    }

    public List<PhotoResponseDto> getPhotosBySort(PhotoSortRequestDto requestDto) {
        Circle circle = circleRepository.findById(requestDto.getCircleId())
                .orElseThrow(() -> new EntityNotFoundException("해당 써클이 없습니다."));
        List<Photo> findPhotos = photoUploadRepository.findAllByCircle(circle);
        switch (requestDto.getSortType()) {
            case "latest":
                findPhotos.sort((o1, o2) -> o2.getCreatedDate().compareTo(o1.getCreatedDate()));
                break;
            case "oldest":
                findPhotos.sort(Comparator.comparing(BaseTimeEntity::getCreatedDate));
                break;
            case "like":
                findPhotos.sort((o1, o2) -> o2.getLikeCount().compareTo(o1.getLikeCount()));
                break;
            default:
                throw new IllegalArgumentException("잘못된 정렬 방식입니다.");
        }
        return PhotoResponseDto.listOf(findPhotos);
    }

    public List<PhotoResponseDto> getLatestPhotosByCircleId(Long circleId) {
        Circle circle = circleRepository.findById(circleId)
                .orElseThrow(() -> new EntityNotFoundException("해당 써클이 없습니다."));
        List<Photo> findPhotos = photoUploadRepository.findAllByCircle(circle);
        findPhotos.sort((o1, o2) -> o2.getCreatedDate().compareTo(o1.getCreatedDate()));

        if (findPhotos.size() >= 4) {
            return PhotoResponseDto.listOf(findPhotos.subList(0, 4));
        }
        return PhotoResponseDto.listOf(findPhotos);
    }
}