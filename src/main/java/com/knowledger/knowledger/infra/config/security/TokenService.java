package com.knowledger.knowledger.infra.config.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Component
public class TokenService {

    @Value("${jwt.secret}")
    private String jwtSecret;
    @Value("${jwt.expiration}")
    private long expirationTime;

    public TokenService() {
    }

    public String generateToken(String email, String role) {
        return JWT.create()
                .withIssuer("knowledger")
                .withSubject(email)
                .withClaim("email", email)
                .withClaim("role", role)
                .withExpiresAt(new Date(System.currentTimeMillis() + expirationTime))
                .sign(Algorithm.HMAC512(jwtSecret));
    }

    public String validateToken(String token) {
        try {
            return JWT.require(Algorithm.HMAC512(jwtSecret))
                    .withIssuer("knowledger")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            return null;
        }
    }

    public String getRole(String token) {
        try {
            return JWT.require(Algorithm.HMAC512(jwtSecret.getBytes()))
                    .build()
                    .verify(token)
                    .getClaim("role").asString();
        } catch (JWTVerificationException exception) {
            return null;
        }
    }

    private Instant generateExpirationDate() {
        return LocalDateTime.now().plusHours(3).toInstant(ZoneOffset.of("-03:00"));
    }

    public boolean isTokenExpired(String token) {
        Date expiration = JWT.require(Algorithm.HMAC512(jwtSecret.getBytes()))
                .build()
                .verify(token)
                .getExpiresAt();
        return expiration.before(new Date());
    }

}
