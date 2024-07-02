package com.code.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;


@Service
public class JwtProvider {
    static SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());

    public static String generateToken(Authentication auth){
        return Jwts.builder().setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime()+864000000))
                .claim("email", auth.getName())
                .signWith(key)
                .compact();
    }

    public static String getEmailFromToken(String jwt){
    Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();

        return String.valueOf(claims.get("email"));

    }

}
