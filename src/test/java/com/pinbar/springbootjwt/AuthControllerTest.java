package com.pinbar.springbootjwt;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class AuthControllerTest {

    @Value("${local.server.port}")
    int port;

    TestRestTemplate template = new TestRestTemplate();

    String jwtForNeo;
    String jwtForMorpheus;

    @Test
    public void testAuthForNeo() {

        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        map.add("programName", "neo");
        map.add("programPassword", "keanu");

        ResponseEntity<String> response = template.postForEntity("http://localhost:" + port + "/authenticate", map,
                String.class);
        assertTrue(response.getBody().contains("JWT"));
        assertEquals(response.getStatusCode().value(), 201);
    }

    @Test
    public void testAuthForMorpheus() {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        map.add("programName", "morpheus");
        map.add("programPassword", "laurence");

        ResponseEntity<String> response = template.postForEntity("http://localhost:" + port + "/authenticate", map,
                String.class);
        assertTrue(response.getBody().contains("JWT"));
        assertEquals(response.getStatusCode().value(), 201);
    }

    @Test
    public void testAuthForWrongPassword() {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        map.add("programName", "neo");
        map.add("programPassword", "laurence");

        ResponseEntity<String> response = template.postForEntity("http://localhost:" + port + "/authenticate", map,
                String.class);
        assertEquals(response.getStatusCode().value(), 400);
    }

    @Test
    public void testAuthForBlankInfo() {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        map.add("programName", "");
        map.add("programPassword", "");

        ResponseEntity<String> response = template.postForEntity("http://localhost:" + port + "/authenticate", map,
                String.class);
        assertEquals(response.getStatusCode().value(), 400);
    }
}