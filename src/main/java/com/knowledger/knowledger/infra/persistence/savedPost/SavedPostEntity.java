package com.knowledger.knowledger.infra.persistence.savedPost;

import com.knowledger.knowledger.infra.persistence.post.PostEntity;
import com.knowledger.knowledger.infra.persistence.user.UserEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "saved_post")
public class SavedPostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private PostEntity post;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    
    public SavedPostEntity() {
    }

    public SavedPostEntity(UserEntity user, PostEntity post, LocalDateTime createdAt) {
        this.user = user;
        this.post = post;
        this.createdAt = createdAt;
    }
    
    public UUID getId() {
        return id;
    }

    public UserEntity getUserId() {
        return user;
    }

    public void setUserId(UserEntity user) {
        this.user = user;
    }

    public PostEntity getPostId() {
        return post;
    }

    public void setPostId(PostEntity post) {
        this.post = post;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
