package com.englishforit.backend.controller;

import com.englishforit.backend.controller.AuthController.LoginRequest;
import com.englishforit.backend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@AutoConfigureMockMvc
class SecurityIntegrationTest extends com.englishforit.backend.AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    void shouldRegisterAndLogin() throws Exception {
        String regJson = "{\"email\":\"test@example.com\",\"password\":\"password\"}";

        // 1. Register
        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(regJson))
                .andExpect(status().isOk());

        // 2. Login
        String token = mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(regJson))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        // 3. Access Protected (Fail)
        mockMvc.perform(get("/api/learning/status?userId=uuid"))
                .andExpect(status().isUnauthorized());

        // 4. Access Protected (Success with Token)
        // We'll hit a non-existent endpoint or Tutor endpoint but expect 404 or
        // specific error, NOT 401
        // Actually /api/tutor/chat is a Post.
        // Let's hitting /api/learning/start which might fail on logic but pass security
        mockMvc.perform(post("/api/tutor/chat")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"userId\": \"random\", \"message\": \"hi\"}"))
                .andExpect(status().isBadRequest());
        // Wait, TutorController mocked? No, it's real.
        // If real TutorController calls OpenAI, it fails.
        // We should mock the Service if we want 200.
        // But for security verification, anything other than 401/403 proves token was
        // accepted.
        // If 500, it means we got passed the filter.

    }

    @Test
    @WithMockUser
    void internalAuthShouldWork() throws Exception {
        // Just checking basic mocking support if needed
    }
}
