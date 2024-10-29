package com.knowledger.knowledger.infra.controller.comment;

import java.time.LocalDateTime;
import java.util.UUID;

public class CommentDetailDTO {
    private UUID id;
    private UUID userId;
    private UUID postId;
    private UUID commentId;
    private Long commentStatusId;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
    private LocalDateTime publishedAt;
    private Boolean isSolution;

    public CommentDetailDTO(UUID id, UUID userId, UUID postId, UUID commentId, Long commentStatusId,
            String content, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt,
            LocalDateTime publishedAt, Boolean isSolution) {
        this.id = id;
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

    public CommentDetailDTO() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public UUID getPostId() {
        return postId;
    }

    public void setPostId(UUID postId) {
        this.postId = postId;
    }

}
