package com.knowledger.knowledger.infra.controller.postImage;

import java.util.UUID;

@SuppressWarnings("unused")
public class PostImageDetailDTO {
    private UUID postId;
    private String path;

    public PostImageDetailDTO(UUID postId, String path) {
        this.postId = postId;
        this.path = path;
    }

    public UUID getPostId() {
        return postId;
    }

    public void setPostId(UUID postId) {
        this.postId = postId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

}