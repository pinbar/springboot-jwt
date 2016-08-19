package com.pinbar.springbootjwt;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ApiControllerTest {

    @Value("${local.server.port}")
    int port;

    TestRestTemplate template = new TestRestTemplate();

    String jwtForNeo;
    String jwtForMorpheus;

    @Before
    public void setUp() {

        MultiValueMap<String, String> neoMap = new LinkedMultiValueMap<String, String>();
        neoMap.add("programName", "neo");
        neoMap.add("programPassword", "keanu");

        ResponseEntity<String> neoResponse = template.postForEntity("http://localhost:" + port + "/authenticate",
                neoMap, String.class);
        jwtForNeo = neoResponse.getBody().substring(5);

        MultiValueMap<String, String> morpheusMap = new LinkedMultiValueMap<String, String>();
        morpheusMap.add("programName", "morpheus");
        morpheusMap.add("programPassword", "laurence");

        ResponseEntity<String> morpheusResponse = template.postForEntity("http://localhost:" + port + "/authenticate",
                morpheusMap, String.class);
        jwtForMorpheus = morpheusResponse.getBody().substring(5);
    }

    @Test
    public void testMegacityWithoutToken() {

        ResponseEntity<String> response = template.getForEntity("http://localhost:" + port + "/api/megacity",
                String.class);
        assertEquals(response.getStatusCode().value(), 401);
    }

    @Test
    public void testMegacityWithInvalidToken() {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + "someMalformedJWT");
        HttpEntity<String> entity = new HttpEntity<String>("test", headers);

        ResponseEntity<String> response = template.exchange("http://localhost:" + port + "/api/megacity",
                HttpMethod.GET, entity, String.class);
        assertEquals(response.getStatusCode().value(), 401);
    }

    @Test
    public void testMegacityWithToken() {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + jwtForNeo);
        HttpEntity<String> entity = new HttpEntity<String>("test", headers);

        ResponseEntity<String> response = template.exchange("http://localhost:" + port + "/api/megacity",
                HttpMethod.GET, entity, String.class);
        assertEquals(response.getStatusCode().value(), 200);
        assertEquals(response.getBody().toString(), "Welcome to the Mega City!");
    }

    @Test
    public void testLevraiWithNeoToken() {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + jwtForNeo);
        HttpEntity<String> entity = new HttpEntity<String>("test", headers);

        ResponseEntity<String> response = template.exchange("http://localhost:" + port + "/api/levrai", HttpMethod.GET,
                entity, String.class);
        assertEquals(response.getStatusCode().value(), 200);
        assertEquals(response.getBody().toString(), "neo, welcome to merovingian's \"secure\" restaurant!");
    }

    @Test
    public void testLevraiWithMorpheusToken() {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + jwtForMorpheus);
        HttpEntity<String> entity = new HttpEntity<String>("test", headers);

        ResponseEntity<String> response = template.exchange("http://localhost:" + port + "/api/levrai", HttpMethod.GET,
                entity, String.class);
        assertEquals(response.getStatusCode().value(), 403);
        assertEquals(response.getBody().toString(),
                "morpheus, you are forbidden from entering merovingian's \"secure\" restaurant!");
    }

}