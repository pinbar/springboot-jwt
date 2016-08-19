package com.pinbar.springbootjwt;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DefaultController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<String> matrix() {
        return new ResponseEntity<String>("Welcome to the Matrix!", HttpStatus.OK);
    }

    @RequestMapping(value = "/metacortex", method = RequestMethod.GET)
    public ResponseEntity<String> metacortex(@RequestParam(value = "name", defaultValue = "Neo") String name,
            @RequestParam(value = "role", defaultValue = "Redpill") String role) {
        return new ResponseEntity<String>("Mr Anderson's \"not so secure\" workplace!", HttpStatus.OK);
    }
}