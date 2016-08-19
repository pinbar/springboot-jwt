package com.pinbar.springbootjwt;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/oracle")
public class ChaosController {

    @RequestMapping(value = "/choice", method = RequestMethod.GET)
    public ResponseEntity<String> matrix() {
        return new ResponseEntity<String>("The equation is balanced!", HttpStatus.OK);
    }
}