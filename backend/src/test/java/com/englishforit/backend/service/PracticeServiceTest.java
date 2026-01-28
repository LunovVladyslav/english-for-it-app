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

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PracticeServiceTest {

    @Mock
    private ChatClient chatClient;

    @InjectMocks
    private PracticeService practiceService;

    @Test
    void generateQuiz_ShouldReturnJsonString() {
        // Arrange
        User user = new User();
        user.setJobRole("Tester");

        String mockQuizJson = "[{\"question\": \"Test?\"}]";
        ChatResponse mockResponse = new ChatResponse(List.of(new Generation(mockQuizJson)));

        when(chatClient.call(any(Prompt.class))).thenReturn(mockResponse);

        // Act
        String result = practiceService.generateQuiz(user, "Grammar");

        // Assert
        assertNotNull(result);
    }
}
