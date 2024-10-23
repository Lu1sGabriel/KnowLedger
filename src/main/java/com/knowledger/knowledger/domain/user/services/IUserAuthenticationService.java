package com.knowledger.knowledger.domain.user.services;

import org.springframework.security.core.AuthenticationException;

public interface IUserAuthenticationService {

    void authenticate(String email, String password) throws AuthenticationException;

}
