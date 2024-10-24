package com.knowledger.knowledger.domain.postStatus;

public class PostStatus {
    private Long id;
    private String name;
    private String description;

    public PostStatus() {
    }

    public PostStatus(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}