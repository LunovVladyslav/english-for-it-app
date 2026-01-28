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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class TutorServiceTest {

    @Mock
    private ChatClient chatClient;

    @InjectMocks
    private TutorService tutorService;

    @Test
    void getTutorResponse_ShouldReturnAiResponse() {
        // Arrange
        User user = new User();
        user.setJobRole("Developer");
        user.setNativeLanguage("English");
        user.setCurrentLevel("B2");
        user.setPrimaryGoal("Fluency");

        String expectedResponse = "Hello, Developer!";

        ChatResponse mockResponse = new ChatResponse(List.of(new Generation(expectedResponse)));
        when(chatClient.call(any(Prompt.class))).thenReturn(mockResponse);

        // Act
        String result = tutorService.getTutorResponse(user, "Hi", "Context");

        // Assert
        assertEquals(expectedResponse, result);
    }
}
