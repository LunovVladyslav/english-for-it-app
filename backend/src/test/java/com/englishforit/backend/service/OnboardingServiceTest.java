package com.englishforit.backend.service;

import com.englishforit.backend.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.Generation;
import org.springframework.ai.chat.prompt.Prompt;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OnboardingServiceTest {

    @Mock
    private ChatClient chatClient;

    @InjectMocks
    private OnboardingService onboardingService;

    @Test
    void startAssessment_ShouldReturnInitialQuestion() {
        // Arrange
        User user = new User();
        user.setJobRole("QA Engineer");

        String expectedQuestion = "Welcome. As a QA, tell me about your testing strategy.";
        ChatResponse mockResponse = new ChatResponse(List.of(new Generation(expectedQuestion)));

        when(chatClient.call(any(Prompt.class))).thenReturn(mockResponse);

        // Act
        String result = onboardingService.startAssessment(user);

        // Assert
        assertEquals(expectedQuestion, result);
    }

    @Test
    void finalizeAssessment_ShouldReturnJson() {
        // Arrange
        User user = new User();
        String history = "Conversation...";
        String expectedJson = "{\"level\": \"B2\"}";

        ChatResponse mockResponse = new ChatResponse(List.of(new Generation(expectedJson)));
        when(chatClient.call(any(Prompt.class))).thenReturn(mockResponse);

        // Act
        String result = onboardingService.finalizeAssessment(user, history);

        // Assert
        assertEquals(expectedJson, result);
    }
}
