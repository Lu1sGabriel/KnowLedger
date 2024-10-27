package com.knowledger.knowledger.infra.config.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TokenService {

    @Value("${jwt.secret}")
    private String jwtSecret;
    @Value("${jwt.expiration}")
    private long expirationTime;

    private static final String ISSUER = "knowledger";
    private static final String ROLE_CLAIM = "role";
    private static final String EMAIL_CLAIM = "email";

    private Algorithm getAlgorithm() {
        return Algorithm.HMAC512(jwtSecret);
    }

    public String generateToken(String email, String role) {
        return JWT.create()
                .withIssuer(ISSUER)
                .withSubject(email)
                .withClaim(EMAIL_CLAIM, email)
                .withClaim(ROLE_CLAIM, role)
                .withExpiresAt(new Date(System.currentTimeMillis() + expirationTime))
                .sign(getAlgorithm());
    }

    public String validateToken(String token) {
        return JWT.require(getAlgorithm())
                .withIssuer(ISSUER)
                .build()
                .verify(token)
                .getSubject();
    }

    public String getRole(String token) {
        return JWT.require(getAlgorithm())
                .withIssuer(ISSUER)
                .build()
                .verify(token)
                .getClaim(ROLE_CLAIM).asString();
    }

}