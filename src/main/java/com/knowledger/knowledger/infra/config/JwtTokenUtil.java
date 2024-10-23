package com.knowledger.knowledger.infra.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenUtil {

    @Value("${jwt.secret}")
    private String jwtSecret;
    @Value("${jwt.expiration}")
    private long expirationTime;

    public JwtTokenUtil() {
    }

    public String generateToken(String email) {
        return JWT.create()
                .withSubject(email)
                .withExpiresAt(new Date(System.currentTimeMillis() + expirationTime))
                .sign(Algorithm.HMAC512(jwtSecret.getBytes()));
    }

    public String validateToken(String token) {
        try {
            return JWT.require(Algorithm.HMAC512(jwtSecret.getBytes()))
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            return null;
        }
    }

    public boolean isTokenExpired(String token) {
        Date expiration = JWT.require(Algorithm.HMAC512(jwtSecret.getBytes()))
                .build()
                .verify(token)
                .getExpiresAt();
        return expiration.before(new Date());
    }

}
