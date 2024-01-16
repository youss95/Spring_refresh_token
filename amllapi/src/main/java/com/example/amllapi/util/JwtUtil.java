package com.example.amllapi.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Map;

public class JwtUtil {

    private static String key = "test1234";

    public static String generateToken(Map<String, Object> valueMap, int min) {
        SecretKey key = null;

        try {
            key = Keys.hmacShaKeyFor(JwtUtil.key.getBytes("UTF-8"));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        String jwtEx = Jwts.builder()
                .setHeader(Map.of("typ","jwt"))
                .setClaims(valueMap)
                .setIssuedAt(Date.from(ZonedDateTime.now().toInstant()))
                .setExpiration(Date.from(ZonedDateTime.now().plusMinutes(min).toInstant()))
                .signWith(key)
                .compact();

        return jwtEx;
    }

    public static Map<String, Object> validateToken(String token) {

        Map<String, Object> claim = null;

        try {
            SecretKey key = Keys.hmacShaKeyFor(JwtUtil.key.getBytes("UTF-8"));

            claim = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            throw new JwtException(e.getMessage());
        }

        return claim;
    }

}
