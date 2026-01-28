package com.englishforit.backend.controller;

import com.englishforit.backend.model.User;
import com.englishforit.backend.repository.UserRepository;
import com.englishforit.backend.service.PracticeService;
import com.englishforit.backend.service.RoleplayService;
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
class PracticeControllerTest {

    @Mock
    private PracticeService practiceService;
    @Mock
    private RoleplayService roleplayService;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private PracticeController practiceController;

    @Test
    void generateQuiz_ShouldReturnJson() {
        UUID userId = UUID.randomUUID();
        User user = new User();
        user.setId(userId);
        Map<String, String> payload = new HashMap<>();
        payload.put("userId", userId.toString());

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(practiceService.generateQuiz(any(User.class), any())).thenReturn("[]");

        ResponseEntity<String> response = practiceController.generateQuiz(payload);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("[]", response.getBody());
    }

    @Test
    void startRoleplay_ShouldReturnResponse() {
        UUID userId = UUID.randomUUID();
        User user = new User();
        user.setId(userId);
        Map<String, String> payload = new HashMap<>();
        payload.put("userId", userId.toString());

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(roleplayService.startRoleplay(any(User.class), any(), any())).thenReturn("Ready");

        ResponseEntity<String> response = practiceController.startRoleplay(payload);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Ready", response.getBody());
    }
}
