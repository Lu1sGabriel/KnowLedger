package com.knowledger.knowledger.infra.controller.user;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public class UserChangePasswordDTO {
    @NotBlank
    private UUID userId;
    @NotBlank
    private String token;
    @NotBlank
    private String oldPassword;
    @NotBlank
    private String newPassword;
    @NotBlank
    private String confirmedNewPassword;

    public UserChangePasswordDTO(UUID userId, String token, String oldPassword, String newPassword, String confirmedNewPassword) {
        this.userId = userId;
        this.token = token;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
        this.confirmedNewPassword = confirmedNewPassword;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmedNewPassword() {
        return confirmedNewPassword;
    }

    public void setConfirmedNewPassword(String confirmedNewPassword) {
        this.confirmedNewPassword = confirmedNewPassword;
    }
}
