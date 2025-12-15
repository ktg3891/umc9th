package com.example.umc9th.global.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtUtil {

    private final SecretKey secretKey;
    private final Duration accessExpiration;

    public JwtUtil(
            @Value("${jwt.token.secretKey}") String secret,
            @Value("${jwt.token.expiration.access}") Long accessExpiration
    ) {
        // 1) 설정값 검증(여기서 터지면 원인 바로 보이게)
        if (secret == null || secret.isBlank()) {
            throw new IllegalStateException("jwt.token.secretKey 설정이 비어있습니다 (application.yml/properties 확인).");
        }
        if (accessExpiration == null || accessExpiration <= 0) {
            throw new IllegalStateException("jwt.token.expiration.access 설정이 비어있거나 0 이하입니다.");
        }

        // 2) secretKey 생성 (Base64로 넣는 걸 권장)
        byte[] keyBytes;
        try {
            keyBytes = Decoders.BASE64.decode(secret);
        } catch (IllegalArgumentException e) {
            // base64가 아니면 그냥 문자열로 사용
            keyBytes = secret.getBytes(java.nio.charset.StandardCharsets.UTF_8);
        }

        // 3) 키 길이 체크(HS256 기준 최소 32바이트 권장/요구)
        if (keyBytes.length < 32) {
            throw new IllegalStateException(
                    "jwt.token.secretKey 길이가 너무 짧습니다. 최소 32바이트(=문자열 32자 이상 또는 base64로 32바이트 이상)로 설정하세요."
            );
        }

        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
        this.accessExpiration = Duration.ofMillis(accessExpiration);
    }


    public String getEmail(String token) {
        try {
            return getClaims(token).getPayload().getSubject();
        } catch (JwtException e) {
            return null;
        }
    }

    public boolean isValid(String token) {
        try {
            getClaims(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    public String createAccessToken(CustomUserDetails user) {
        String role = user.getAuthorities().stream()
                .map(org.springframework.security.core.GrantedAuthority::getAuthority)
                .collect(java.util.stream.Collectors.joining(","));
        return createAccessToken(user.getUsername(), role);
    }

    public String createAccessToken(String email, String role) {
        java.time.Instant now = java.time.Instant.now();
        return io.jsonwebtoken.Jwts.builder()
                .subject(email)
                .claim("role", role)
                .issuedAt(java.util.Date.from(now))
                .expiration(java.util.Date.from(now.plus(accessExpiration)))
                .signWith(secretKey)
                .compact();
    }



    private String createToken(CustomUserDetails user, Duration expiration) {
        Instant now = Instant.now();

        String authorities = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        return Jwts.builder()
                .subject(user.getUsername())
                .claim("role", authorities)
                .claim("email", user.getUsername())
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plus(expiration)))
                .signWith(secretKey)
                .compact();
    }

    private Jws<Claims> getClaims(String token) throws JwtException {
        return Jwts.parser()
                .verifyWith(secretKey)
                .clockSkewSeconds(60)
                .build()
                .parseSignedClaims(token);
    }
}
