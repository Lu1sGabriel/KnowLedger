package com.knowledger.knowledger.infra.controller.post;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class PostDetailDTO {
    private UUID id;
    private UUID userId;
    private Long postTypeId;
    private Long postStatusId;
    private List<CommentSummaryDTO> comments;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
    private LocalDateTime publishedAt;

    public PostDetailDTO() {
    }

    public PostDetailDTO(UUID id, UUID userId, Long postTypeId, Long postStatusId, List<CommentSummaryDTO> comments,
            String title, String content, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt,
            LocalDateTime publishedAt) {
        this.id = id;
        this.userId = userId;
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

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public Long getPostTypeId() {
        return postTypeId;
    }

    public void setPostTypeId(Long postTypeId) {
        this.postTypeId = postTypeId;
    }

    public Long getPostStatusId() {
        return postStatusId;
    }

    public void setPostStatusId(Long postStatusId) {
        this.postStatusId = postStatusId;
    }

    public List<CommentSummaryDTO> getComments() {
        return comments;
    }

    public void setComments(List<CommentSummaryDTO> comments) {
        this.comments = comments;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public static class CommentSummaryDTO {
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

        public CommentSummaryDTO() {
        }

        public CommentSummaryDTO(UUID id, UUID userId, UUID postId, UUID commentId, Long commentStatusId,
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

        public UUID getId() {
            return id;
        }

        public void setId(UUID id) {
            this.id = id;
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

}
