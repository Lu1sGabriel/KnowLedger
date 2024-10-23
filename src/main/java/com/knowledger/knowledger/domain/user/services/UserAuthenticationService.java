package com.knowledger.knowledger.domain.user.services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserAuthenticationService implements IUserAuthenticationService{

    private final AuthenticationManager _authenticationManager;

    public UserAuthenticationService(AuthenticationManager authenticationManager) {
        _authenticationManager = authenticationManager;
    }

    @Override
    public void authenticate(String email, String password) throws AuthenticationException {
        Authentication authentication = _authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
