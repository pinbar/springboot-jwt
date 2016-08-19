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

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ChaosControllerTest {

    @Value("${local.server.port}")
    int port;

    private TestRestTemplate template = new TestRestTemplate();

    @Test
    public void testChaos() {
        boolean successResponse = false;
        boolean failureResponse = false;
        for (int i = 0; i < 15; i++) {
            ResponseEntity<String> response = template.getForEntity("http://localhost:" + port + "/oracle/choice",
                    String.class);
            if (response.getStatusCodeValue() == 200) {
                successResponse = true;
            } else if (response.getStatusCodeValue() == 500) {
                failureResponse = true;
            }
        }
        assertTrue(successResponse);
        assertTrue(failureResponse);
    }
}