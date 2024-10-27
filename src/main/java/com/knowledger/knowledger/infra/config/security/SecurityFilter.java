package com.knowledger.knowledger.infra.config.security;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.knowledger.knowledger.infra.exceptions.BusinessException;
import com.knowledger.knowledger.infra.persistence.user.IUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    private final TokenService _tokenService;
    private final IUserRepository _iUserRepository;

    public SecurityFilter(TokenService tokenService, IUserRepository userRepository) {
        _tokenService = tokenService;
        _iUserRepository = userRepository;
    }

    @SuppressWarnings("NullableProblems")
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        Optional.ofNullable(recoverToken(request))
                .ifPresent(this::authenticateToken);

        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader(AUTHORIZATION_HEADER);
        if (authHeader != null ) {
            return authHeader.replace(BEARER_PREFIX, "");
        }
        return null;
    }

    private void authenticateToken(String token) {
        try {
            var email = _tokenService.validateToken(token);
            var role = _tokenService.getRole(token);
            authenticateUser(email, role);
        } catch (JWTVerificationException exception) {
            //  Conectar erro, token inválido ou usuário não encontrado
        }
    }

    private void authenticateUser(String email, String role) {
        var user = _iUserRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException("Usuário não encontrado.", HttpStatus.NOT_FOUND));

        var authority = new SimpleGrantedAuthority(role);
        var authentication = new UsernamePasswordAuthenticationToken(user, null, Collections.singleton(authority));

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

}