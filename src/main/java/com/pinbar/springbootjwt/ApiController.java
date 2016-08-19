package com.pinbar.springbootjwt;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
public class ApiController {

    @Autowired
    CryptoUtil cryptoUtil;

    @RequestMapping(value = "/megacity", method = RequestMethod.GET)
    public String megacity() {
        return "Welcome to the Mega City!";
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/levrai", method = RequestMethod.GET)
    public ResponseEntity<String> levrai(HttpServletRequest request) {
        HashMap<String, Object> claims = (HashMap<String, Object>) request.getAttribute("claims");
        String programName = (String) claims.get("program");
        try {
            programName = cryptoUtil.decrypt(programName);
            if (!StringUtils.isEmpty(programName) && programName.equals("neo")) {
                return new ResponseEntity<String>(programName + ", welcome to merovingian\'s \"secure\" restaurant!",
                        HttpStatus.OK);
            } else {
                return new ResponseEntity<String>(
                        programName + ", you are forbidden from entering merovingian\'s \"secure\" restaurant!",
                        HttpStatus.FORBIDDEN);
            }
        } catch (Exception e) {
            return new ResponseEntity<String>("An unexpected error occurred during decryption",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}