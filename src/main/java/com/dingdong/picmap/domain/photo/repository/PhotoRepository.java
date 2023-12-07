package com.dingdong.picmap.domain.photo.repository;

import com.dingdong.picmap.domain.photo.entity.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhotoRepository extends JpaRepository<Photo, Long> {
    List<Photo> findAllByOrderByLikeCountDesc();
}
