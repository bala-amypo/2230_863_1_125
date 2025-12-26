package com.example.demo.security;

import com.example.demo.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

    // ✅ Minimum 32 characters (256 bits)
    private static final String SECRET_KEY =
            "my-super-secret-jwt-key-that-is-at-least-32-chars";

    private static final long VALIDITY_IN_MS = 86400000; // 24 hours

    private final Key key;

    public JwtTokenProvider() {
        this.key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public String generateToken(Authentication authentication, User user) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + VALIDITY_IN_MS);

        return Jwts.builder()
                .setSubject(user.getEmail())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
}
