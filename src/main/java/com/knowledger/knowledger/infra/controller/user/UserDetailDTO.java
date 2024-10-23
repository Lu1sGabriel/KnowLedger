package com.knowledger.knowledger.infra.controller.user;

import com.knowledger.knowledger.commom.Constants;
import com.knowledger.knowledger.infra.controller.user.role.RoleDetailDTO;

import java.time.LocalDateTime;
import java.util.UUID;

public class UserDetailDTO {
    private UUID id;
    private String name;
    private String email;
    private RoleDetailDTO role;
    private String createdAt;
    private String updatedAt;
    private String deletedAt;
    private boolean isActive;

    public UserDetailDTO() {
    }

    public UserDetailDTO(UUID id, String name, String email, RoleDetailDTO role, LocalDateTime createdAt,
                         LocalDateTime updatedAt, LocalDateTime deletedAt, boolean isActive) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
        this.createdAt = Constants.toDateString(createdAt);
        this.updatedAt = Constants.toDateString(updatedAt);
        this.deletedAt = Constants.toDateString(deletedAt);
        this.isActive = isActive;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public RoleDetailDTO getRole() {
        return role;
    }

    public void setRole(RoleDetailDTO role) {
        this.role = role;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(String deletedAt) {
        this.deletedAt = deletedAt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }


}
