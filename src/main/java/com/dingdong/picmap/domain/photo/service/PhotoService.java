package com.dingdong.picmap.domain.photo.service;

import com.dingdong.picmap.domain.circle.entity.Circle;
import com.dingdong.picmap.domain.circle.repository.CircleRepository;
import com.dingdong.picmap.domain.photo.dto.PhotoLocationResponseDto;
import com.dingdong.picmap.domain.photo.dto.PhotoResponseDto;
import com.dingdong.picmap.domain.photo.entity.Photo;
import com.dingdong.picmap.domain.photo.repository.PhotoRepository;
import com.dingdong.picmap.domain.photo.repository.PhotoUploadRepository;
import com.dingdong.picmap.domain.user.entity.User;
import com.dingdong.picmap.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PhotoService {

    private final PhotoUploadRepository photoUploadRepository;
    private final PhotoRepository photoRepository;
    private final UserRepository userRepository;
    private final CircleRepository circleRepository;

    public PhotoResponseDto getPhotoByPhotoId(Long id) {
        Photo findPhoto = photoUploadRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 사진이 없습니다. id=" + id));
        return PhotoResponseDto.of(findPhoto);
    }

    public List<PhotoResponseDto> getPhotosByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다. id=" + userId));
        List<Photo> findPhotos = photoUploadRepository.findAllByUserId(user);
        return PhotoResponseDto.listOf(findPhotos);
    }

    @Transactional
    public String deletePhoto(List<Long> photoIdList) {
        photoIdList.stream()
                .map(photoId -> photoRepository.findById(photoId)
                        .orElseThrow(() -> new EntityNotFoundException("해당 사진이 없습니다.")))
                .forEach(photoRepository::delete);
        return "success";
    }

    public List<PhotoResponseDto> getPhotosByCircleId(Long circleId) {
        Circle circle = circleRepository.findById(circleId)
                .orElseThrow(() -> new IllegalArgumentException("해당 써클이 없습니다. id=" + circleId));
        List<Photo> findPhotos = photoUploadRepository.findAllByCircleId(circle);
        return PhotoResponseDto.listOf(findPhotos);
    }

    public PhotoLocationResponseDto getLocation(Long photoId) {
        Photo photo = photoRepository.findById(photoId)
                .orElseThrow(() -> new IllegalArgumentException("해당 사진이 없습니다. id=" + photoId));
        return PhotoLocationResponseDto.builder()
                .id(photo.getId())
                .filePath(photo.getFilePath())
                .latitude(photo.getLatitude())
                .longitude(photo.getLongitude())
                .build();
    }
}