package com.knowledger.knowledger.infra.controller.post;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.knowledger.knowledger.infra.controller.comment.CommentDetailDTO;
import com.knowledger.knowledger.infra.persistence.user.UserEntity;

public class PostDetailDTO {
    private UUID id;
    private UserEntity user;
    private Long postTypeId;
    private Long postStatusId;
    private List<CommentDetailDTO> comments;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
    private LocalDateTime publishedAt;

    public PostDetailDTO() {
    }

    public PostDetailDTO(UUID id, UserEntity user, Long postTypeId, Long postStatusId, List<CommentDetailDTO> comments,
            String title, String content, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt,
            LocalDateTime publishedAt) {
        this.id = id;
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

    public List<CommentDetailDTO> getComments() {
        return comments;
    }

    public void setComments(List<CommentDetailDTO> comments) {
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

}
