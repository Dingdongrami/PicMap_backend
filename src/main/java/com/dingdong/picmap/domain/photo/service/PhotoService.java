package com.dingdong.picmap.domain.photo.service;

import com.dingdong.picmap.domain.photo.dto.PhotoResponse;
import com.dingdong.picmap.domain.photo.entity.Photo;
import com.dingdong.picmap.domain.photo.repository.PhotoRepository;
import com.dingdong.picmap.domain.photo.repository.PhotoUploadRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PhotoService {

    private final PhotoUploadRepository photoUploadRepository;
    private final PhotoRepository photoRepository;

    public PhotoResponse getPhotoByPhotoId(Long id) {
        Photo findPhoto = photoUploadRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 사진이 없습니다. id=" + id));
        return PhotoResponse.of(findPhoto.getId(), findPhoto.getFilePath());
    }

    public List<PhotoResponse> getPhotoByUserId(Long userId) {
        List<Photo> findPhotos = photoUploadRepository.findAllByUserId(userId);
        return PhotoResponse.listOf(findPhotos);
    }

    @Transactional
    public String deletePhoto(Long photoId) {
        Photo photo = photoRepository.findById(photoId)
                .orElseThrow(() -> new IllegalArgumentException("해당 사진이 없습니다. id=" + photoId));
        photoRepository.delete(photo);
        return photoId + " deleted";
    }
}