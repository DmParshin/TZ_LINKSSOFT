package com.example.tz_linkssoft;

import com.example.tz_linkssoft.captcha.Captcha;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

//Test get captcha

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class HttpRequestTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testGetCaptchaSuccess() {
        final String getUrl = "http://localhost:" + port + "/get-captcha";
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<HttpHeaders> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(getUrl, HttpMethod.GET, entity, String.class);
        assertEquals("image/png", response.getHeaders().get("Content-Type").get(0));
    }

    @Test
    public void testCheckCaptchaError() {
        final String postUrl = "http://localhost:" + port + "/post-captcha";
        Captcha captcha = new Captcha("123456789", "qwe123");
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Captcha> request = new HttpEntity<>(captcha, headers);
        ResponseEntity<String> result = restTemplate.exchange(postUrl, HttpMethod.POST, request, String.class);
        assertEquals("error", result.getBody());
    }

    @Test
    public void testCheckCaptchaSuccess() {
        final String getUrl = "http://localhost:" + port + "/get-captcha";
        final String postUrl = "http://localhost:" + port + "/post-captcha";

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<HttpHeaders> responseEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(getUrl, HttpMethod.GET, responseEntity, String.class);

        String cookies = response.getHeaders().get("Set-Cookie").stream().collect(Collectors.joining(";"));
        Captcha captcha = new Captcha(response.getHeaders().get("request_id").get(0), response.getHeaders().get("captcha_string").get(0));

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Cookie", cookies);
        HttpEntity<Captcha> requestEntity = new HttpEntity<>(captcha, httpHeaders);
        ResponseEntity<String> result = restTemplate.exchange(postUrl, HttpMethod.POST, requestEntity, String.class);

        assertEquals("success", result.getBody());
    }
}
