package com.als.SMore.user.login.util;

import io.jsonwebtoken.Claims;
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
        Date expiredDate = Date.from(Instant.now().plusMillis(time));

        String jwt = Jwts.builder()
                .signWith(key, SignatureAlgorithm.HS256)
                .subject(userId).issuedAt(startedDate)
                .expiration(expiredDate).compact();
        return jwt;
    }

    public String create(String userId, Long time,Map<String,String> claim){
        Date startedDate = Date.from(Instant.now());
        Date expiredDate = Date.from(Instant.now().plusMillis(time));

        String jwt = Jwts.builder()
                .signWith(key, SignatureAlgorithm.HS256)
                .subject(userId).issuedAt(startedDate)
                .claims(claim)  // 오브젝트 타입으로 넣기
                .expiration(expiredDate).compact();
        return jwt;
    }

    public String generateAccessToken(Long userId,Map<String,String> claim){
        if (claim.isEmpty()){
            return create(String.valueOf(userId),accessTokenValidTime);
        }
        return create(String.valueOf(userId),accessTokenValidTime,claim);
    }

    public String generateRefreshToken(Long userId,Map<String,String> claim){
        if (claim.isEmpty()){
            return create(String.valueOf(userId),refreshTokenValidTime);
        }
        return create(String.valueOf(userId),refreshTokenValidTime,claim);
    }

    public String validate(String jwt) {
        String subject = null;
            subject = Jwts.parser().verifyWith(key)
                    .build().parseSignedClaims(jwt)
                    .getPayload().getSubject();

        return subject;
    }

    // 기존의 jwt를 받아서 다른 jwt를 생성하는 기능
    public String createRenewToken(String jwt, Long studyPk, String role){
        // jwt를 분해함
        Claims payload = Jwts.parser().verifyWith(key)
                .build().parseSignedClaims(jwt)
                .getPayload();

        Map<String,String> roleList = new HashMap<>();

        Set<String> keySet = payload.keySet();
        for (String key : keySet) {
            if(!key.equals("sub") && !key.equals("iat") && !key.equals("exp")){
                System.out.println(key);
                roleList.put(key, (String) payload.get(key));
            }

        }

        roleList.put(String.valueOf(studyPk),role);

        // 새로운 토큰 발급
        String renewToken = Jwts.builder()
                .signWith(key, SignatureAlgorithm.HS256)
                .subject(payload.getSubject())
                .issuedAt(payload.getIssuedAt())
                .claims(roleList)  // 오브젝트 타입으로 넣기
                .expiration(payload.getExpiration())
                .compact();
        return renewToken;
    }

    // 토큰에 studyPk에 대한 역할이 있는지 확인하는 함수
    public boolean isCheckedRole(String jwt, String studyPk){
        Claims payload = Jwts.parser().verifyWith(key)
                .build().parseSignedClaims(jwt)
                .getPayload();
        if (payload.get(studyPk) == null){
            return false;
        }
        return true;
    }

    public String getRole(String jwt, String studyPk){
        String role = (String) Jwts.parser().verifyWith(key)
                .build().parseSignedClaims(jwt)
                .getPayload().get(studyPk);
        return role;
    }

}
