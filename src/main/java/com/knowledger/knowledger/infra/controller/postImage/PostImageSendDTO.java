package com.knowledger.knowledger.infra.controller.postImage;

import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public class PostImageSendDTO {
    private UUID postId;
    private String path;
    private MultipartFile file;

    public PostImageSendDTO(UUID postId, String path, MultipartFile file) {
        this.postId = postId;
        this.path = path;
        this.file = file;
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

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

}