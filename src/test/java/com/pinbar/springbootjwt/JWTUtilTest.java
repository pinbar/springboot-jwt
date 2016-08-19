package com.pinbar.springbootjwt;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.auth0.jwt.JWTExpiredException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JWTUtilTest {

    @Autowired
    private JWTUtil jwtUtil;

    final String neoToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoicmVkcGlsbCIsInByb2dyYW0iOiJuZW8iLCJleHAiOjE0NzE1Mjc3NzUsImlhdCI6MTQ3MTUyNzcxNX0.rkRvlHo9Ud0ZXtj0NaGMcCdyywvNJMqYdsbOC9-aqOY";

    @Test
    public void testSign() {

        HashMap<String, Object> claims = new HashMap<String, Object>();
        claims.put("program", "neo");
        claims.put("role", "redpill");
        claims.put("exp", 1471527775);
        claims.put("iat", 1471527715);
        String jwt = jwtUtil.sign(claims);
        assertEquals(jwt, neoToken);
    }

    @Test
    public void testSignNullClaim() {

        String jwt = jwtUtil.sign(null);
        assertEquals(jwt, null);
    }

    @Test(expected = JWTExpiredException.class)
    public void testVerifyExpiredToken() throws Exception {
        jwtUtil.verify(neoToken);
    }

    @Test(expected = IllegalStateException.class)
    public void testVerifyMalformedToken() throws Exception {
        jwtUtil.verify("someMalformedToken");
    }

}