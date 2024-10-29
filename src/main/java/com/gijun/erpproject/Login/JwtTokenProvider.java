package com.gijun.erpproject.Login;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String secretKey;

    private final RedisTemplate<String, String> redisTemplate;

    private static final long TOKEN_VALID_MILLISECONDS = 1000L * 60 * 60; // 1시간

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    // JWT 토큰 생성
    public String createToken(String username, List<String> roles) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("roles", roles);
        Date now = new Date();

        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + TOKEN_VALID_MILLISECONDS))
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .compact();

        // Redis에 토큰 저장 (중복 로그인 방지)
        redisTemplate.opsForValue()
                .set("JWT:" + username, token, TOKEN_VALID_MILLISECONDS, TimeUnit.MILLISECONDS);

        return token;
    }

    // 토큰에서 username 추출
    public String getUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // 토큰에서 권한 정보 추출
    public List<String> getRoles(String token) {
        return (List<String>) Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("roles", List.class);
    }

    // 토큰 유효성 검증
    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
                    .build()
                    .parseClaimsJws(token);

            String username = claims.getBody().getSubject();
            String savedToken = redisTemplate.opsForValue().get("JWT:" + username);

            return token.equals(savedToken) && !isTokenExpired(claims.getBody().getExpiration());
        } catch (Exception e) {
            return false;
        }
    }

    private boolean isTokenExpired(Date expiration) {
        return expiration.before(new Date());
    }

    // 토큰 무효화 (로그아웃 시 사용)
    public void invalidateToken(String username) {
        redisTemplate.delete("JWT:" + username);
    }
}