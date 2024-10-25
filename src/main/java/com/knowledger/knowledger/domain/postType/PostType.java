package com.knowledger.knowledger.domain.postType;

public class PostType {
    private Long id;
    private String name;
    private String description;

    public PostType() {
    }

    public PostType(String name, String description) {
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