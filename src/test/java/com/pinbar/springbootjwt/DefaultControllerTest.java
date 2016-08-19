package com.pinbar.springbootjwt;

import static org.junit.Assert.assertEquals;

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
public class DefaultControllerTest {

    @Value("${local.server.port}")
    int port;

    private TestRestTemplate template = new TestRestTemplate();

    @Test
    public void testBaseUrl() {
        ResponseEntity<String> response = template.getForEntity("http://localhost:" + port + "/", String.class);
        assertEquals(response.getStatusCode().value(), 200);
        assertEquals(response.getBody().toString(), "Welcome to the Matrix!");
    }

    @Test
    public void testMetacortex() {
        ResponseEntity<String> response = template.getForEntity("http://localhost:" + port + "/metacortex",
                String.class);
        assertEquals(response.getStatusCode().value(), 200);
        assertEquals(response.getBody().toString(), "Mr Anderson's \"not so secure\" workplace!");
    }

    @Test
    public void testMetacortexUnsupportedMethod() {
        ResponseEntity<String> response = template.postForEntity("http://localhost:" + port + "/metacortex", null,
                String.class);
        assertEquals(response.getStatusCode().value(), 405);
    }

    @Test
    public void testBadResource() {
        ResponseEntity<String> response = template.getForEntity("http://localhost:" + port + "/someApi", String.class);
        assertEquals(response.getStatusCode().value(), 404);
    }
}