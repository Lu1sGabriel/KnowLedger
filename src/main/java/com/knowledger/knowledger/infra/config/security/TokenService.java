package com.knowledger.knowledger.infra.config.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.knowledger.knowledger.commom.Constants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
public class TokenService {

    private final Algorithm _algorithm;
    private final long _expirationTime;

    public TokenService(@Value("${jwt.secret}") String jwtSecret, @Value("${jwt.expiration}") long expirationTime) {
        _algorithm = Algorithm.HMAC512(jwtSecret);
        _expirationTime = expirationTime;
    }

    public String generateToken(UUID userId, String email, String name, String role) {
        return JWT.create()
                .withIssuer(Constants.JWT.ISSUER)
                .withSubject(email)
                .withClaim(Constants.JWT.EMAIL_CLAIM, email)
                .withClaim(Constants.JWT.USER_NAME_CLAIM, name)
                .withClaim(Constants.JWT.USER_ID_CLAIM, userId.toString())
                .withClaim(Constants.JWT.ROLE_CLAIM, role)
                .withExpiresAt(new Date(System.currentTimeMillis() + _expirationTime))
                .sign(_algorithm);
    }

    private DecodedJWT verifyToken(String token) {
        return JWT.require(_algorithm)
                .withIssuer(Constants.JWT.ISSUER)
                .build()
                .verify(token);
    }

    public String validateToken(String token) {
        return verifyToken(token).getSubject();
    }

    public String getRole(String token) {
        return verifyToken(token).getClaim(Constants.JWT.ROLE_CLAIM).asString();
    }

}