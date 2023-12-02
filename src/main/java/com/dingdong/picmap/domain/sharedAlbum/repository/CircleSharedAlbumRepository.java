package com.dingdong.picmap.domain.sharedAlbum.repository;

import com.dingdong.picmap.domain.circle.entity.Circle;
import com.dingdong.picmap.domain.photo.entity.Photo;
import com.dingdong.picmap.domain.sharedAlbum.entity.CircleSharedAlbum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CircleSharedAlbumRepository extends JpaRepository<CircleSharedAlbum, Long> {

    @Query("SELECT p FROM Photo p "
            + "INNER JOIN CircleSharedAlbum cSA ON cSA.photo = p "
            + "WHERE cSA.circle = :circle")
    List<Photo> findAllByCircle(@Param("circle") Circle circle);

    void deleteByPhoto(Photo photo);
}
