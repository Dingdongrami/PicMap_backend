package com.dingdong.picmap.domain.photo.repository;

import com.dingdong.picmap.domain.circle.entity.Circle;
import com.dingdong.picmap.domain.photo.entity.Photo;
import com.dingdong.picmap.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhotoUploadRepository extends JpaRepository<Photo, Long> {

    @Query("SELECT p FROM Photo p "
        + "INNER JOIN CircleSharedAlbum csa ON csa.photo = p "
        + "WHERE csa.user = :user")
    List<Photo> findAllByUser(@Param("user") User user);

    @Query("SELECT p FROM Photo p "
        + "INNER JOIN CircleSharedAlbum csa ON csa.photo = p "
        + "WHERE csa.circle = :circle")
    List<Photo> findAllByCircle(@Param("circle")Circle circle);
}