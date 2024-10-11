package com.knowledger.knowledger.infra.gateways.user;

import com.knowledger.knowledger.domain.entities.user.User;

import java.util.UUID;

public interface IUserGateway {

    public User create(String name, String email, String password);
    public User changePassword(UUID userId, String token, String oldPassword, String newPassword, String confirmedNewPassword) throws Exception;

}
