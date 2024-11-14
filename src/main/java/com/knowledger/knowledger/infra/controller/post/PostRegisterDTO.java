package com.knowledger.knowledger.infra.controller.post;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class PostRegisterDTO {

    @NotNull
    private UUID userId;
    @NotNull
    private Long postTypeId;
    @NotNull
    private Long departmentId;
    @NotNull
    private String title;
    @NotBlank
    private String content;

    public PostRegisterDTO() {
    }

    public PostRegisterDTO(UUID userId, Long postTypeId, Long departmentId, String title, String content) {
        this.userId = userId;
        this.postTypeId = postTypeId;
        this.departmentId = departmentId;
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

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
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