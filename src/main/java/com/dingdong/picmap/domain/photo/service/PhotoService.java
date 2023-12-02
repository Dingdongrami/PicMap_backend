package com.dingdong.picmap.domain.photo.service;

import com.dingdong.picmap.domain.circle.entity.Circle;
import com.dingdong.picmap.domain.circle.repository.CircleRepository;
import com.dingdong.picmap.domain.circle.repository.CircleUserRepository;
import com.dingdong.picmap.domain.photo.dto.PhotoLocationResponseDto;
import com.dingdong.picmap.domain.photo.dto.PhotoResponseDto;
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
import java.util.List;
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

    public List<PhotoResponseDto> getPhotosByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("해당 유저가 없습니다."));
        List<Photo> findPhotos = photoUploadRepository.findAllByUserId(user);
        return PhotoResponseDto.listOf(findPhotos);
    }

    public List<PhotoResponseDto> getPhotosByPublicCircleByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("해당 유저가 없습니다."));
        List<Circle> publicCircles = circleUserRepository.findCirclesByUserId(user)
                .stream()
                .filter(Circle::getIsPublic)
                .collect(Collectors.toList());

        List<Photo> publicCirclesAllPhotos = publicCircles.stream()
                .map(circleSharedAlbumRepository::findAllByCircle)
                .flatMap(List::stream)
                .collect(Collectors.toList());

        return PhotoResponseDto.listOf(publicCirclesAllPhotos);
    }

    public List<PhotoResponseDto> getPhotosByAllCirclesByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("해당 유저가 없습니다."));
        List<Circle> allCircles = circleUserRepository.findCirclesByUserId(user);
        List<Photo> allCirclesAllPhotos = allCircles.stream()
                .map(circleSharedAlbumRepository::findAllByCircle)
                .flatMap(List::stream)
                .collect(Collectors.toList());
        return PhotoResponseDto.listOf(allCirclesAllPhotos);
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

    public List<PhotoResponseDto> getPhotosByCircleId(Long circleId) {
        Circle circle = circleRepository.findById(circleId)
                .orElseThrow(() -> new EntityNotFoundException("해당 써클이 없습니다."));
        List<Photo> findPhotos = photoUploadRepository.findAllByCircleId(circle);
        return PhotoResponseDto.listOf(findPhotos);
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
}