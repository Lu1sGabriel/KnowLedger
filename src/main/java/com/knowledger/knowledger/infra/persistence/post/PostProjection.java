package com.knowledger.knowledger.infra.persistence.post;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface PostProjection {

    UUID getPostId();

    String getTitle();

    String getContent();

    Long getPostTypeId();

    Long getPostStatusId();

    LocalDateTime getCreatedAt();

    LocalDateTime getUpdatedAt();

    LocalDateTime getDeletedAt();

    LocalDateTime getPublishedAt();

    String getPostUserName();

    UUID getPostUserId();

    List<CommentProjection> getComments();

    interface CommentProjection {

        UUID getId();

        String getContent();

        Long getCommentStatusId();

        UUID getPostId();

        UUID getCommentId();

        UUID getUserId();

        String getUserName();

        LocalDateTime getCreatedAt();

        LocalDateTime getUpdatedAt();

        LocalDateTime getDeletedAt();

        LocalDateTime getPublishedAt();
    }

}
