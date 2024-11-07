package com.knowledger.knowledger.domain.comment;

import com.knowledger.knowledger.commom.Constants;
import com.knowledger.knowledger.domain.post.Post;
import com.knowledger.knowledger.domain.user.User;

import java.time.LocalDateTime;
import java.util.UUID;

public class Comment {

    private UUID id;
    private Post post;
    private User user;
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

    public Comment(UUID userId, UUID postId, UUID commentId, String content) {
        this();
        this.user = new User(userId);
        this.post = new Post(postId);
        this.commentId = commentId;
        // TODO:: Criar serviço de validação de COMMENT com IA
        this.commentStatusId = Constants.CommentStatus.APPROVED;
        this.content = content;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.publishedAt = LocalDateTime.now();
        this.isSolution = false;
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

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UUID getPostId() {
        return post != null ? post.getId() : null;
    }
}
