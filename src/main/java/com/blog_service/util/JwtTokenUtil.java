package com.blog_service.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;


@Component
public class JwtTokenUtil {

    // 토큰 서명에 사용할 비밀 키 (안전한 곳에 저장해야 합니다)
    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // 토큰 유효 기간 (밀리초 단위)
    private final long validityInMilliseconds = 3600000; // 1시간

    // JWT 토큰 생성
    public String createToken(String username) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(key)
                .compact();
    }

    // JWT 토큰 검증
    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = getClaims(token);

            return !claims.getBody().getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            // 유효하지 않은 토큰
            return false;
        }
    }

    // 토큰에서 사용자 이름 추출
    public String getUsername(String token) {
        return getClaims(token).getBody().getSubject();
    }

    private Jws<Claims> getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
    }
}
