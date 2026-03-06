package com.polytech.commandes.com.polytech.commandes.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret:secret}")
    private String secret;

    @Value("${jwt.expiration:3600000}") // 1h
    private long expirationMillis;

    private Algorithm algorithm() {
        return Algorithm.HMAC256(secret.getBytes());
    }

    public String generateToken(UserDetails userDetails) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + expirationMillis);
        return JWT.create()
                .withSubject(userDetails.getUsername())
                .withIssuedAt(now)
                .withExpiresAt(expiry)
                .sign(algorithm());
    }

    public String extractUsername(String token) {
        try {
            DecodedJWT jwt = JWT.require(algorithm()).build().verify(token);
            return jwt.getSubject();
        } catch (JWTVerificationException e) {
            return null;
        }
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        return username != null && username.equals(userDetails.getUsername());
    }
}
