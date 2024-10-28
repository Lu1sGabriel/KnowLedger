package com.knowledger.knowledger.infra.config.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TokenService {

    private final Algorithm _algorithm;
    private final long _expirationTime;

    private static final String ISSUER = "knowledger";
    private static final String ROLE_CLAIM = "role";
    private static final String EMAIL_CLAIM = "email";

    public TokenService(@Value("${jwt.secret}") String jwtSecret,
                        @Value("${jwt.expiration}") long expirationTime) {
        _algorithm = Algorithm.HMAC512(jwtSecret);
        _expirationTime = expirationTime;
    }

    public String generateToken(String email, String role) {
        return JWT.create()
                .withIssuer(ISSUER)
                .withSubject(email)
                .withClaim(EMAIL_CLAIM, email)
                .withClaim(ROLE_CLAIM, role)
                .withExpiresAt(new Date(System.currentTimeMillis() + _expirationTime))
                .sign(_algorithm);
    }

    private DecodedJWT verifyToken(String token) {
        return JWT.require(_algorithm)
                .withIssuer(ISSUER)
                .build()
                .verify(token);
    }

    public String validateToken(String token) {
        return verifyToken(token).getSubject();
    }

    public String getRole(String token) {
        return verifyToken(token).getClaim(ROLE_CLAIM).asString();
    }

}