package com.knowledger.knowledger.infra.persistence.comment;

import com.knowledger.knowledger.infra.persistence.post.PostEntity;
import com.knowledger.knowledger.infra.persistence.user.UserEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "comment")
public class CommentEntity {

    public CommentEntity() {
    }

    public CommentEntity(Boolean isSolution, LocalDateTime publishedAt, LocalDateTime deletedAt, LocalDateTime updatedAt, LocalDateTime createdAt, String content, Long commentStatusId, UUID commentId, PostEntity post, UserEntity user) {
        this.isSolution = isSolution;
        this.publishedAt = publishedAt;
        this.deletedAt = deletedAt;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
        this.content = content;
        this.commentStatusId = commentStatusId;
        this.commentId = commentId;
        this.post = post;
        this.user = user;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(name = "post_id", nullable = false)
    private PostEntity post;

    @Column(name = "comment_id")
    private UUID commentId;

    @Column(name = "comment_status_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
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


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public PostEntity getPost() {
        return post;
    }

    public void setPost(PostEntity post) {
        this.post = post;
    }

    public UUID getCommentId() {
        return commentId;
    }

    public void setCommentId(UUID commentId) {
        this.commentId = commentId;
    }

    public Long getCommentStatusId() {
        return commentStatusId;
    }

    public void setCommentStatusId(Long commentStatusId) {
        this.commentStatusId = commentStatusId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }

    public LocalDateTime getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(LocalDateTime publishedAt) {
        this.publishedAt = publishedAt;
    }

    public Boolean getIsSolution() {
        return isSolution;
    }

    public void setIsSolution(Boolean isSolution) {
        this.isSolution = isSolution;
    }
}
