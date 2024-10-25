package com.knowledger.knowledger.domain.user.services;

import com.knowledger.knowledger.infra.config.JwtTokenUtil;
import com.knowledger.knowledger.infra.exceptions.BusinessException;
import com.knowledger.knowledger.infra.persistence.user.IUserRepository;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserAuthenticationService implements IUserAuthenticationService {

    private final AuthenticationManager _authenticationManager;
    private final JwtTokenUtil _jwtTokenUtil;
    private final IUserRepository _iUserRepository;

    public UserAuthenticationService(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil, IUserRepository iUserRepository) {
        _authenticationManager = authenticationManager;
        _jwtTokenUtil = jwtTokenUtil;
        _iUserRepository = iUserRepository;
    }

    @Override
    public String authenticate(String email, String password) throws AuthenticationException {
        Authentication authentication = _authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        var user = _iUserRepository.findByEmail(email).
                orElseThrow(() -> new BusinessException("Usuário não encontrado", HttpStatus.NOT_FOUND));

        return _jwtTokenUtil.generateToken(user.getEmail(), user.getRole().getName());
    }

}