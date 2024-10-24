package com.knowledger.knowledger.infra.persistence.like;

import java.time.LocalDateTime;
import java.util.UUID;

import com.knowledger.knowledger.infra.persistence.user.UserEntity;

import jakarta.persistence.*;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Entity
@Table(name = "like")
@Getter
@Setter
@NoArgsConstructor
public class LikeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(name = "created_at")
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(name = "update_at")
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Column(name = "is_active")
    private Boolean isActive;

    public LikeEntity(UserEntity user, LocalDateTime createdAt, LocalDateTime updatedAt, Boolean isActive) {
        this.user = user;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isActive = true;
    }

}