package com.knowledger.knowledger.infra.persistence.post;

import com.knowledger.knowledger.infra.persistence.comment.CommentEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "post")
@Getter
@Setter
@NoArgsConstructor
public class PostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "post_type_id")
    private Long postTypeId;

    @Column(name = "department_id")
    private Long departmentId;

    @Column(name = "post_status_id")
    private Long postStatusId;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "post_id", referencedColumnName = "id")
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

    public PostEntity(UUID userId, Long postTypeId, Long departmentId, Long postStatusId,
                      List<CommentEntity> comments, String title, String content, LocalDateTime createdAt,
                      LocalDateTime updatedAt, LocalDateTime deletedAt, LocalDateTime publishedAt) {
        this.userId = userId;
        this.postTypeId = postTypeId;
        this.departmentId = departmentId;
        this.postStatusId = postStatusId;
        this.comments = comments;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
        this.publishedAt = publishedAt;
    }

}