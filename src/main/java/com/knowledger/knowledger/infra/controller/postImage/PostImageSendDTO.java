package com.knowledger.knowledger.infra.controller.postImage;

import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public class PostImageSendDTO {
    private UUID postId;
    private MultipartFile file;

    public PostImageSendDTO(UUID postId, MultipartFile file) {
        this.postId = postId;
        this.file = file;
    }

    public UUID getPostId() {
        return postId;
    }

    public void setPostId(UUID postId) {
        this.postId = postId;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

}