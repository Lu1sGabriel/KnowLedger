package com.knowledger.knowledger.infra.persistence.post;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.knowledger.knowledger.infra.persistence.comment.CommentEntity;
import com.knowledger.knowledger.infra.persistence.user.UserEntity;

import jakarta.persistence.*;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "post")
@Getter
@Setter
@NoArgsConstructor
public class PostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(name = "post_type_id")
    private Long postTypeId;

    @Column(name = "post_status_id")
    private Long postStatusId;

    @OneToMany(mappedBy = "post")
    @Column(name = "comments")
    private List<CommentEntity> comments;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "published_at")
    private LocalDateTime publishedAt;

    public PostEntity(UserEntity user, Long postTypeId, Long postStatusId, List<CommentEntity> comments, String title,
            String content, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt,
            LocalDateTime publishedAt) {
        this.user = user;
        this.postTypeId = postTypeId;
        this.postStatusId = postStatusId;
        this.comments = comments;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
        this.publishedAt = publishedAt;
    }

}