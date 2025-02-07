package com.admin.utils;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Map;


public class JwtUtils {

    public static String createToken(Map<String, Object> claims) {
        String token = Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + Constants.JWT_TTL))
                .signWith(SignatureAlgorithm.HS256, Constants.JWT_SECRET)
                .compact();
        return token;
    }

    public static Claims parseToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(Constants.JWT_SECRET)
                .parseClaimsJws(token)
                .getBody();

        return claims;
    }
}
