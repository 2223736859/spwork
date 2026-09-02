package com.example.spwork.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * JWT 工具类：生成、解析、校验令牌
 * 配置项来自 application.yml 的 jwt.*
 */
@Data
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtUtil {

    private String secret;
    private long expiration;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 生成 JWT 令牌
     * Payload 只存非敏感信息：stuId(subject)、学号、姓名、签发时间、过期时间
     */
    public String generateToken(Integer stuId, String stuNo, String stuName) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);
        return Jwts.builder()
                .setSubject(String.valueOf(stuId))
                .claim("stuNo", stuNo)
                .claim("stuName", stuName)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * 解析令牌，返回 Payload（Claims）
     * 签名不匹配或已过期会抛异常
     */
    public Claims parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 校验令牌是否有效（签名正确且未过期）
     */
    public boolean validateToken(String token) {
        try {
            parseToken(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 从令牌中取出 stuId
     */
    public Integer getStuId(String token) {
        return Integer.valueOf(parseToken(token).getSubject());
    }
}
