package com.knowledger.knowledger.infra.gateways.user;

import com.knowledger.knowledger.domain.user.User;

import java.util.UUID;

public interface IUserGateway {

    User register(String name, String email, String password, String confirmedPassword) throws Exception;

    User changePassword(UUID userId, String token, String oldPassword, String newPassword, String confirmedNewPassword) throws Exception;

    User getById(UUID id) throws Exception;

    Object login(String email, String password);
}
