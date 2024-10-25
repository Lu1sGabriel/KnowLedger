package com.knowledger.knowledger.domain.user.services;

import com.knowledger.knowledger.infra.config.security.TokenService;
import com.knowledger.knowledger.infra.persistence.user.IUserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserAuthenticationService implements IUserAuthenticationService {

    private final TokenService _tokenService;
    private final PasswordEncoder _passwordEncoder;

    public UserAuthenticationService(TokenService tokenService, PasswordEncoder passwordEncoder) {
        _tokenService = tokenService;
        _passwordEncoder = passwordEncoder;
    }

    @Override
    public String login(String email, String payloadPassword, String userPassword, String role) throws AuthenticationException {

        if (_passwordEncoder.matches(payloadPassword, userPassword)){
            return _tokenService.generateToken(email, role);
        }

        return null;

    }
}
