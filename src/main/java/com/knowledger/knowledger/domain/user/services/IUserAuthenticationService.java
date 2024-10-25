package com.knowledger.knowledger.domain.user.services;

import org.springframework.security.core.AuthenticationException;

public interface IUserAuthenticationService {

    String login(String email, String payloadPassword, String userPassword, String role) throws AuthenticationException;

}
