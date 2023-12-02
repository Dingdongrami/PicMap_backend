package com.dingdong.picmap.domain.photo.entity;

import com.dingdong.picmap.domain.comment.entity.Comment;
import com.dingdong.picmap.domain.global.BaseTimeEntity;
import com.dingdong.picmap.domain.like.entity.Like;
import com.dingdong.picmap.domain.user.entity.User;
import jdk.jshell.Snippet;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Table(name = "photos")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Photo extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "photo_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @Column(name = "file_path")
    private String filePath;

    @Column(name = "shooting_date", columnDefinition = "TIMESTAMP")
    private LocalDateTime shootingDate;

    private Double latitude;
    private Double longitude;

    @Column(name = "like_count", nullable = false)
    @ColumnDefault("0")
    private Integer likeCount;

    @Column(name = "comment_count", nullable = false)
    @ColumnDefault("0")
    private Integer commentCount;

    @OneToMany(mappedBy = "photo", cascade = CascadeType.ALL)
    private List<Like> likes;

    @OneToMany(mappedBy = "photo", cascade = CascadeType.ALL)
    private List<Comment> comments;

    public Photo(User user, String filePath) {
        this.user = user;
        this.filePath = filePath;
    }

    //== 설정 메서드 ==//
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void setMetaData(Double latitude, Double longitude, LocalDateTime shootingDate) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.shootingDate = shootingDate;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }
}