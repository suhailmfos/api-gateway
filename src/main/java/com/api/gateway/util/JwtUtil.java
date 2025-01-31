package com.api.gateway.util;

import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;

import java.security.Key;

public class JwtUtil {
    private static final Key key = Keys.secretKeyFor(SignatureAlgorithmm.HS256);

    public static String generateToken(String username) {
        return ServerHttpSecurity.OAuth2ResourceServerSpec.JwtSpec.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1-day validity
                .signWith(key)
                .compact();
    }
}
