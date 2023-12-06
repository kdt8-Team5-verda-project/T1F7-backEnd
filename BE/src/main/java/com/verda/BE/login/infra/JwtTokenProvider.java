package com.verda.BE.login.infra;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

// 1. JWT 토큰을 만들어주는 유틸 클래스
@Component
public class JwtTokenProvider {
    private final Key key;
    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 30;

    // 1. jwt 데이터 준비
    public JwtTokenProvider(@Value("${jwt.secret-key}") String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    // 2. jwt 생성
    public String generateUser(Long memberId, String email, String name, Date expiredAt) {
        long now = System.currentTimeMillis();
        Date accessTokenExpiredAt = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
//        return Jwts.builder()
//                .setSubject(subject)
//                .setExpiration(expiredAt)
//                .signWith(key, SignatureAlgorithm.HS256)
//                .compact();
        Map<String, Object> claims = new HashMap<>();

        claims.put("email", email);
        claims.put("name", name);
        claims.put("userId", memberId);




        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(memberId.toString())
                .setClaims(claims)  // 여러 정보를 claim에 추가
                .setIssuedAt(new Date(now))
                .setExpiration(expiredAt)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
    public String generateFund(Long memberId, String email, String name, Date expiredAt) {
        long now = System.currentTimeMillis();
        Date accessTokenExpiredAt = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
//        return Jwts.builder()
//                .setSubject(subject)
//                .setExpiration(expiredAt)
//                .signWith(key, SignatureAlgorithm.HS256)
//                .compact();
        Map<String, Object> claims = new HashMap<>();

        claims.put("email", email);
        claims.put("name", name);
        claims.put("fmId", memberId);




        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(memberId.toString())
                .setClaims(claims)  // 여러 정보를 claim에 추가
                .setIssuedAt(new Date(now))
                .setExpiration(expiredAt)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // 3. jwt 검증
    public String extractSubject(String accessToken) {
        Claims claims = parseClaims(accessToken);
        return claims.getSubject();
    }

    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(accessToken)
                    .getBody();
        } catch (ExpiredJwtException e) {

            return e.getClaims();
        }
    }
}