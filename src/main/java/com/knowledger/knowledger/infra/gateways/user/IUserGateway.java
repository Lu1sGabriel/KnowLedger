package com.knowledger.knowledger.infra.gateways.user;

import com.knowledger.knowledger.domain.user.User;

import java.util.UUID;

public interface IUserGateway {

    User create(String name, String email, String password);

    User changePassword(UUID userId, String token, String oldPassword, String newPassword, String confirmedNewPassword) throws Exception;

}