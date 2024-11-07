package com.knowledger.knowledger.domain.post;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.knowledger.knowledger.commom.Constants;
import com.knowledger.knowledger.domain.comment.Comment;
import com.knowledger.knowledger.domain.user.User;

public class Post {

    private UUID id;
    private User user;
    private Long postTypeId;
    private Long postStatusId;
    private List<Comment> comments;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
    private LocalDateTime publishedAt;

    public Post() {
        this.id = UUID.randomUUID();
    }

    public Post(UUID id) {
        this.id = id;
    }

    public Post(UUID userId, Long postTypeId, String title, String content) {
        this();
        this.user = new User(userId);
        this.postTypeId = postTypeId;
        // TODO:: Criar serviço de validaçaão de POST com IA
        this.postStatusId = Constants.PostStatus.APPROVED;
        this.title = title;
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

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
