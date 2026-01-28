package com.englishforit.backend.controller;

import com.englishforit.backend.model.User;
import com.englishforit.backend.repository.UserRepository;
import com.englishforit.backend.service.TutorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TutorControllerTest {

    @Mock
    private TutorService tutorService;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TutorController tutorController;

    @Test
    void chat_ShouldReturnResponse_WhenUserExists() {
        // Arrange
        UUID userId = UUID.randomUUID();
        User user = new User();
        user.setId(userId);

        Map<String, String> payload = new HashMap<>();
        payload.put("userId", userId.toString());
        payload.put("message", "Hello");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(tutorService.getTutorResponse(eq(user), eq("Hello"), any())).thenReturn("AI Response");

        // Act
        ResponseEntity<String> response = tutorController.chat(payload);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("AI Response", response.getBody());
    }

    @Test
    void chat_ShouldReturnBadRequest_WhenUserIdMissing() {
        Map<String, String> payload = new HashMap<>();
        ResponseEntity<String> response = tutorController.chat(payload);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}
