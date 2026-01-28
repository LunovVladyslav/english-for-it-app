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

@ExtendWith(MockitoExtension.class)
class RoleplayServiceTest {

    @Mock
    private ChatClient chatClient;

    @InjectMocks
    private RoleplayService roleplayService;

    @Test
    void startRoleplay_ShouldReturnResponse() {
        // Arrange
        User user = new User();
        user.setJobRole("Dev");
        String expected = "Hello, I am your PM.";

        ChatResponse mockResponse = new ChatResponse(List.of(new Generation(expected)));
        when(chatClient.call(any(Prompt.class))).thenReturn(mockResponse);

        // Act
        String result = roleplayService.startRoleplay(user, "Meeting", "PM");

        // Assert
        assertEquals(expected, result);
    }

    @Test
    void provideFeedback_ShouldReturnAnalysis() {
        // Arrange
        User user = new User();
        String history = "Dialog...";
        String expected = "Good job.";

        ChatResponse mockResponse = new ChatResponse(List.of(new Generation(expected)));
        when(chatClient.call(any(Prompt.class))).thenReturn(mockResponse);

        // Act
        String result = roleplayService.provideFeedback(user, history);

        // Assert
        assertEquals(expected, result);
    }
}
