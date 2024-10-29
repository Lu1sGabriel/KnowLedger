package com.knowledger.knowledger.infra.controller.comment;

import java.util.UUID;

public class CommentRegisterDTO {

    private UUID userId;
    private UUID postId;
    private UUID commentId;
    private Long commentStatusId;
    private String content;

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

}
