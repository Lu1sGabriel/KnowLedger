package com.knowledger.knowledger.infra.config.security;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.knowledger.knowledger.infra.exceptions.BusinessException;
import com.knowledger.knowledger.infra.persistence.user.IUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityFilter.class);
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

        var token = recoverToken(request);
        if (token != null) {
            authenticateToken(token);
        }

        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader(AUTHORIZATION_HEADER);
        if (authHeader != null && authHeader.startsWith(BEARER_PREFIX)) {
            return authHeader.substring(BEARER_PREFIX.length());
        }
        return null;
    }

    private void authenticateToken(String token) {
        try {
            var email = _tokenService.validateToken(token);
            var role = _tokenService.getRole(token);
            authenticateUser(email, role);
        } catch (JWTVerificationException | BusinessException exception) {
            LOGGER.warn("Falha na autenticação do token: {}", exception.getMessage());
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