package com.pinbar.springbootjwt;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    JWTUtil jwtUtil;

    @Autowired
    CryptoUtil cryptoUtil;

    @Autowired
    AuthService authService;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<String> authenticate(@ModelAttribute Program program) {

        if (authService.authenticate(program)) {

            HashMap<String, Object> claims = new HashMap<String, Object>();
            try {
                claims.put("program", cryptoUtil.encrypt(program.getProgramName()));
                claims.put("role", cryptoUtil.encrypt("redpill"));
            } catch (Exception e) {
                return new ResponseEntity<String>("An unexpected error occurred during encryption",
                        HttpStatus.INTERNAL_SERVER_ERROR);
            }
            String token = jwtUtil.sign(claims);
            return new ResponseEntity<String>("JWT: " + token, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<String>("Wrong credentials", HttpStatus.BAD_REQUEST);
        }
    }
}