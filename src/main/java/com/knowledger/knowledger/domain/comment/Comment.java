package com.knowledger.knowledger.domain.comment;

import com.knowledger.knowledger.infra.persistence.post.PostEntity;
import com.knowledger.knowledger.infra.persistence.user.UserEntity;

import java.time.LocalDateTime;
import java.util.UUID;

public class Comment {

    private UUID id;
    private UserEntity user;
    private PostEntity post;
    private UUID commentId;
    private Long commentStatusId;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
    private LocalDateTime publishedAt;
    private Boolean isSolution;

    public Comment() {
        this.id = UUID.randomUUID();
    }

    public Comment(UserEntity user, PostEntity post, UUID commentId, Long commentStatusId, String content) {
        this();
        this.user = user;
        this.post = post;
        this.commentId = commentId;
        this.commentStatusId = commentStatusId;
        this.content = content;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.publishedAt = LocalDateTime.now();
    }

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
