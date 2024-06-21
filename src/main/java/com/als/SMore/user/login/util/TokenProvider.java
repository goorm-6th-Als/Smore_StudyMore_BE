package com.als.SMore.user.login.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.*;

@Component
public class TokenProvider {
    @Value("${jwt.secret-key}")
    private String secretKey;
    private SecretKey key;
    private final long accessTokenValidTime = (60 * 1000) *60 *3; // 3시간
    private final long refreshTokenValidTime = (60 * 1000) * 60 * 24 * 14; // 7일

    @PostConstruct
    protected void init() {
        // key를 base64로 인코딩
        //String encodedKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String create(String userId, Long time){
        Date startedDate = Date.from(Instant.now());
        Date expiredDate = Date.from(Instant.now().plusMillis(accessTokenValidTime));

        String jwt = Jwts.builder()
                .signWith(key, SignatureAlgorithm.HS256)
                .subject(userId).issuedAt(startedDate)
                .claim("role","admin")  // 오브젝트 타입으로 넣기
                .expiration(expiredDate).compact();
        return jwt;
    }

    public String generateAccessToken(Long userId){
        return create(String.valueOf(userId),accessTokenValidTime);
    }

    public String generateRefreshToken(Long userId){
        return create(String.valueOf(userId),refreshTokenValidTime);
    }

    public String validate(String jwt) {
        String subject = null;
        try {
            subject = Jwts.parser().verifyWith(key)
                    .build().parseSignedClaims(jwt)
                    .getPayload().getSubject();
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }

        return subject;
    }

}
