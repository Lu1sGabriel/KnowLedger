package com.knowledger.knowledger.infra.gateways.user;

import com.knowledger.knowledger.domain.user.User;

import java.util.Map;
import java.util.UUID;

public interface IUserGateway {

    User register(String name, String email, String password, String confirmedPassword) ;

    User changePassword(UUID userId, String token, String oldPassword, String newPassword, String confirmedNewPassword) ;

    User getById(UUID id) ;

    Map<String, String> login(String email, String password);
}
