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
    private final String secret;
    private final long validityInMs;
    private final Key key;
    
    public JwtTokenProvider() {
        this.secret = "mySecretKey";
        this.validityInMs = 86400000; // 24 hours
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }
    
    public JwtTokenProvider(String secret, long validityInMs) {
        this.secret = secret;
        this.validityInMs = validityInMs;
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }
    
    public String generateToken(Authentication authentication, User user) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + validityInMs);
        
        return Jwts.builder()
            .setSubject(user.getEmail())
            .setIssuedAt(now)
            .setExpiration(expiryDate)
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();
    }
}