package com.englishforit.backend.controller;

import com.englishforit.backend.model.User;
import com.englishforit.backend.repository.UserRepository;
import com.englishforit.backend.service.OnboardingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;
import java.util.UUID;
import java.util.Map;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OnboardingControllerTest {

    @Mock
    private OnboardingService onboardingService;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private OnboardingController onboardingController;

    @Test
    void startAssessment_ShouldReturnGreeting() {
        UUID userId = UUID.randomUUID();
        User user = new User();
        user.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(onboardingService.startAssessment(any(User.class))).thenReturn("Hello");

        ResponseEntity<String> response = onboardingController.startAssessment(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Hello", response.getBody());
    }

    @Test
    void finalizeAssessment_ShouldReturnJson() {
        UUID userId = UUID.randomUUID();
        User user = new User();
        user.setId(userId);

        Map<String, String> payload = new HashMap<>();
        payload.put("userId", userId.toString());

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(onboardingService.finalizeAssessment(any(User.class), any())).thenReturn("{}");

        ResponseEntity<String> response = onboardingController.finalizeAssessment(payload);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("{}", response.getBody());
    }
}
