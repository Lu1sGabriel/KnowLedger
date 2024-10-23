package com.knowledger.knowledger.infra.controller.user;

public class UserTokenAuthenticationDTO {

    private String token;

    public UserTokenAuthenticationDTO() {
    }

    public UserTokenAuthenticationDTO(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
