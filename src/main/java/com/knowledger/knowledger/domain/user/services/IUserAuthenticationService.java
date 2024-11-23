package com.knowledger.knowledger.domain.user.services;

import java.util.UUID;

import org.springframework.security.core.AuthenticationException;

public interface IUserAuthenticationService {

    String login(UUID userId, String name, String email, String payloadPassword, String userPassword, String role)
            throws AuthenticationException;

}
