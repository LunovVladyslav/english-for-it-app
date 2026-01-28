package com.englishforit.backend.controller;

import com.englishforit.backend.AbstractIntegrationTest;
import com.englishforit.backend.model.User;
import com.englishforit.backend.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class LearningControllerIntTest extends AbstractIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @Test
    void startSession_ShouldCreateSession() {
        // 1. Register
        String email = "learner" + java.util.UUID.randomUUID() + "@test.com";
        String password = "password";
        String regJson = String.format("{\"email\":\"%s\",\"password\":\"%s\"}", email, password);

        restTemplate.postForEntity("/api/auth/register",
                new org.springframework.http.HttpEntity<>(regJson, headers("")), String.class);

        // 2. Login
        ResponseEntity<String> loginResp = restTemplate.postForEntity("/api/auth/login",
                new org.springframework.http.HttpEntity<>(regJson, headers("")), String.class);
        String token = loginResp.getBody();

        // 3. Start Session (requires userId)
        // We need the ID. The register/login flow doesn't return ID easily (Login
        // returns token).
        // SecurityIntegrationTest showed we can use "Bearer token".
        // But /api/learning/start expects userId param?
        // Let's refactor /api/learning/start to take userId from Token/Context ideally,
        // but if it takes param, we need it.
        // For now, let's fetch user from Repo (assuming validation/transaction doesn't
        // hide it).
        // If Repo fails, we can't easily get ID without an endpoint returning it.
        // Let's assume we can fetch it.

        User user = userRepository.findByEmail(email).orElseThrow();

        org.springframework.http.HttpHeaders authHeaders = headers(token);

        ResponseEntity<String> response = restTemplate.postForEntity(
                "/api/learning/start?userId=" + user.getId(),
                new org.springframework.http.HttpEntity<>(null, authHeaders),
                String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    private org.springframework.http.HttpHeaders headers(String token) {
        org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
        headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
        if (token != null && !token.isEmpty()) {
            headers.setBearerAuth(token);
        }
        return headers;
    }
}
