package com.dingdong.picmap.domain.like.service;

import com.amazonaws.services.kms.model.NotFoundException;
import com.dingdong.picmap.domain.like.dto.LikeRequestDto;
import com.dingdong.picmap.domain.like.entity.Like;
import com.dingdong.picmap.domain.like.repository.LikeRepository;
import com.dingdong.picmap.domain.photo.entity.Photo;
import com.dingdong.picmap.domain.photo.repository.PhotoRepository;
import com.dingdong.picmap.domain.user.entity.User;
import com.dingdong.picmap.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class LikeService {

    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final PhotoRepository photoRepository;

    public void addLike(LikeRequestDto requestDto) {
        User user = userRepository.findById(requestDto.getUserId()).orElseThrow(
                () -> new IllegalArgumentException("해당 유저가 없습니다."));
        Photo photo = photoRepository.findById(requestDto.getPhotoId()).orElseThrow(
                () -> new IllegalArgumentException("해당 사진이 없습니다."));

        if (likeRepository.findByUserAndPhoto(user, photo).isPresent()) {
            throw new IllegalArgumentException("이미 좋아요를 누른 사진입니다.");
        }

        Like like = Like.builder()
                .user(user)
                .photo(photo)
                .build();
        likeRepository.save(like);
    }

    public void deleteLike(LikeRequestDto requestDto) {
        User user = userRepository.findById(requestDto.getUserId()).orElseThrow(
                () -> new NotFoundException("해당 유저가 없습니다."));
        Photo photo = photoRepository.findById(requestDto.getPhotoId()).orElseThrow(
                () -> new NotFoundException("해당 사진이 없습니다."));
        Like like = likeRepository.findByUserAndPhoto(user, photo).orElseThrow(
                () -> new NotFoundException("해당 좋아요가 없습니다."));
        likeRepository.delete(like);
    }
}
