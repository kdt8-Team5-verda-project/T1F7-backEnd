package org.example.oAuth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class JwtDecode {
    private final String secretKey;

    public JwtDecode(String secretKey) {
        this.secretKey = secretKey;
    }

    public Claims executeDecode(String authorization) {
        String jwtToken = authorization.substring(7);
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken).getBody();
    }
}
