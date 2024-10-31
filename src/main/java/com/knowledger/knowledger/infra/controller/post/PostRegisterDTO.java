package com.knowledger.knowledger.infra.controller.post;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class PostRegisterDTO {

    @NotNull
    private UUID userId;
    private Long postTypeId;
    private Long postStatusId;
    @NotNull
    private String title;
    @NotBlank
    private String content;

    public PostRegisterDTO() {
    }

    public PostRegisterDTO(UUID userId, Long postTypeId, Long postStatusId, String title, String content) {
        this.userId = userId;
        this.postTypeId = postTypeId;
        this.postStatusId = postStatusId;
        this.title = title;
        this.content = content;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
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

}
