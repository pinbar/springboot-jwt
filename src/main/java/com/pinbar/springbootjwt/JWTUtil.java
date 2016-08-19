package com.pinbar.springbootjwt;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWTSigner;
import com.auth0.jwt.JWTVerifier;

@Component
public class JWTUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiresinseconds}")
    private long expiresInSeconds;

    public String sign(HashMap<String, Object> claims) {

        if (null != claims) {
            final long iat = System.currentTimeMillis() / 1000l;
            final long exp = iat + expiresInSeconds;

            final JWTSigner signer = new JWTSigner(secret);
            // claims.put("iss", issuer);
            claims.putIfAbsent("exp", exp);
            claims.putIfAbsent("iat", iat);

            final String jwt = signer.sign(claims);
            return jwt;
        } else {
            return null;
        }
    }

    public Map<String, Object> verify(String jwt) throws Exception {
        try {
            final JWTVerifier jwtVerifier = new JWTVerifier(secret);
            final Map<String, Object> claims = jwtVerifier.verify(jwt);
            return claims;
        } catch (Exception e) {
            // Invalid Token. Log and return false
            throw e;
        }
    }
}
