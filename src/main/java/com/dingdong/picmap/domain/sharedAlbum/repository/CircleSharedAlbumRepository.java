package com.dingdong.picmap.domain.sharedAlbum.repository;

import com.dingdong.picmap.domain.sharedAlbum.entity.CircleSharedAlbum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CircleSharedAlbumRepository extends JpaRepository<CircleSharedAlbum, Long> {
}
