package com.dingdong.picmap.domain.photo.repository;

import com.dingdong.picmap.domain.photo.entity.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoUploadRepository extends JpaRepository<Photo, Long> {

}