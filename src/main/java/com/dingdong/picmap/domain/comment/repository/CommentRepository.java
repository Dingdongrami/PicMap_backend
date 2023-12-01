package com.dingdong.picmap.domain.comment.repository;

import com.dingdong.picmap.domain.comment.entity.Comment;
import com.dingdong.picmap.domain.photo.entity.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByPhoto(Photo photo);
}
