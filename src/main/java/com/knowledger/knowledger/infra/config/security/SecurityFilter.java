package com.knowledger.knowledger.infra.config.security;

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

@Component
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final IUserRepository _iUserRepository;

    public SecurityFilter(TokenService tokenService, IUserRepository iUserRepository) {
        this.tokenService = tokenService;
        _iUserRepository = iUserRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {

        String path = request.getRequestURI();

        if (path.equals("/users/login") || path.equals("/users/register")) {
            filterChain.doFilter(request, response);
            return;
        }

        var token = this.recoverToken(request);
        var login = tokenService.validateToken(token);

        if (login != null) {
            var user = _iUserRepository.findByEmail(login)
                    .orElseThrow(() -> new BusinessException("Usuário não encontrado. Por favor, entre em contato com o setor de TI.", HttpStatus.NOT_FOUND));
            var authorities = Collections.singletonList(new SimpleGrantedAuthority(user.getRole().getName()));
            var authentication = new UsernamePasswordAuthenticationToken(user, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);

    }

    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null) return null;
        return authHeader.replace("Bearer ", "");
    }

}
