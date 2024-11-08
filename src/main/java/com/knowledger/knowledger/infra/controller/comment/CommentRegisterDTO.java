package com.knowledger.knowledger.infra.controller.comment;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CommentRegisterDTO {

    @NotNull
    private UUID userId;
    @NotNull
    private UUID postId;
    private UUID commentId;
    @NotBlank
    private String content;

    public CommentRegisterDTO() {
    }

    public CommentRegisterDTO(@NotNull UUID userId, @NotNull UUID postId, UUID commentId, @NotBlank String content) {
        this.userId = userId;
        this.postId = postId;
        this.commentId = commentId;
        this.content = content;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
