package com.dingdong.picmap.domain.comment.service;

import com.dingdong.picmap.domain.comment.dto.CommentRequestDto;
import com.dingdong.picmap.domain.comment.dto.CommentResponseDto;
import com.dingdong.picmap.domain.comment.dto.CommentUpdateRequestDto;
import com.dingdong.picmap.domain.comment.entity.Comment;
import com.dingdong.picmap.domain.comment.repository.CommentRepository;
import com.dingdong.picmap.domain.photo.entity.Photo;
import com.dingdong.picmap.domain.photo.repository.PhotoRepository;
import com.dingdong.picmap.domain.user.entity.User;
import com.dingdong.picmap.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final UserRepository userRepository;
    private final PhotoRepository photoRepository;
    private final CommentRepository commentRepository;

    public CommentResponseDto addComment(CommentRequestDto requestDto) {
        User user = userRepository.findById(requestDto.getUserId()).orElseThrow(
                () -> new EntityNotFoundException("해당 유저가 없습니다."));
        Photo photo = photoRepository.findById(requestDto.getPhotoId()).orElseThrow(
                () -> new EntityNotFoundException("해당 사진이 없습니다."));

        Comment comment = Comment.builder()
                .comment(requestDto.getComment())
                .user(user)
                .photo(photo)
                .build();
        Comment savedComment = commentRepository.save(comment);
        photo.setCommentCount(photo.getCommentCount() + 1);
        return new CommentResponseDto(savedComment);
    }

    public List<CommentResponseDto> getCommentList(Long photoId) {
        Photo photo = photoRepository.findById(photoId).orElseThrow(
                () -> new EntityNotFoundException("해당 사진이 없습니다."));
        List<Comment> commentList = commentRepository.findAllByPhoto(photo);

        // 댓글 최신순 정렬
        commentList.sort((o1, o2) -> o2.getCreatedDate().compareTo(o1.getCreatedDate()));
        return commentList.stream()
                .map(CommentResponseDto::new)
                .collect(Collectors.toList());
    }

    public CommentResponseDto updateComment(CommentUpdateRequestDto requestDto) {
        Comment comment = commentRepository.findById(requestDto.getCommentId()).orElseThrow(
                () -> new EntityNotFoundException("해당 댓글이 없습니다."));
        comment.update(requestDto.getComment());
        return new CommentResponseDto(comment);
    }

    public String deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new EntityNotFoundException("해당 댓글이 없습니다."));
        commentRepository.delete(comment);
        if (comment.getPhoto().getCommentCount() > 0) {
            comment.getPhoto().setCommentCount(comment.getPhoto().getCommentCount() - 1);
        }
        return "success";
    }

}
