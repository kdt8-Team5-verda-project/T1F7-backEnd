package com.verda.BE.common;

import com.verda.BE.JwtConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;

public class JwtDecode {
    @Autowired
    private final JwtConfig jwtConfig;
    public JwtDecode(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    public Claims executeDecode(String authorization){
        String jwtToken = authorization.substring(7);
        Claims claims = Jwts.parser().setSigningKey(jwtConfig.getSecretKey()).parseClaimsJws(jwtToken).getBody();
        return claims;
    }
}
