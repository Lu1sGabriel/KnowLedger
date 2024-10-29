package com.knowledger.knowledger.infra.config.security;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.knowledger.knowledger.commom.Constants;
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
import java.util.List;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenService _tokenService;
    private final IUserRepository _iUserRepository;

    private final List<String> openEndpoints = List.of(
            Constants.NoRequiredAuthorizedPath.USER_LOGIN,
            Constants.NoRequiredAuthorizedPath.USER_REGISTER
    );

    public SecurityFilter(TokenService tokenService, IUserRepository userRepository) {
        _tokenService = tokenService;
        _iUserRepository = userRepository;
    }

    @SuppressWarnings("NullableProblems")
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();

        boolean isPublic = openEndpoints.stream().anyMatch(path::startsWith);

        if (isPublic) {
            filterChain.doFilter(request, response);
            return;
        }

        var token = recoverToken(request);
        if (token != null) {
            authenticateToken(token);
        }
        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader(Constants.Security.AUTHORIZATION_HEADER);
        if (authHeader != null && authHeader.startsWith(Constants.Security.BEARER_PREFIX)) {
            return authHeader.replace(Constants.Security.BEARER_PREFIX, "");
        }
        return null;
    }

    private void authenticateToken(String token) {
        try {
            var email = _tokenService.validateToken(token);
            var role = _tokenService.getRole(token);
            authenticateUser(email, role);
        } catch (JWTVerificationException | BusinessException exception) {
            throw new BusinessException("Token inválido.", HttpStatus.BAD_REQUEST);
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