package com.knowledger.knowledger.infra.persistence.post;

import com.knowledger.knowledger.infra.persistence.comment.CommentEntity;
import com.knowledger.knowledger.infra.persistence.user.UserEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "post")
public class PostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(name = "post_type_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long postTypeId;

    @Column(name = "post_status_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
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

    // Construtor vazio
    public PostEntity() {
    }

    // Construtor com todos os atributos, exceto o próprio id
    public PostEntity(UserEntity user, Long postTypeId, Long postStatusId, String title, String content,
                      LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt, LocalDateTime publishedAt) {
        this.user = user;
        this.postTypeId = postTypeId;
        this.postStatusId = postStatusId;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
        this.publishedAt = publishedAt;
    }

    // Getters and Setters
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

