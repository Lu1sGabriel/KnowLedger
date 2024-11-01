package com.knowledger.knowledger.infra.persistence.comment;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "comment")
@Getter
@Setter
@NoArgsConstructor
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "post_id", nullable = false)
    private UUID postId;

    @Column(name = "comment_id")
    private UUID commentId;

    @Column(name = "comment_status_id")
    private Long commentStatusId;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "published_at")
    private LocalDateTime publishedAt;

    @Column(name = "is_solution")
    private Boolean isSolution;

    public CommentEntity(UUID userId, UUID postId, UUID commentId, Long commentStatusId, String content,
            LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt, LocalDateTime publishedAt,
            Boolean isSolution) {
        this.userId = userId;
        this.postId = postId;
        this.commentId = commentId;
        this.commentStatusId = commentStatusId;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
        this.publishedAt = publishedAt;
        this.isSolution = isSolution;
    }

}