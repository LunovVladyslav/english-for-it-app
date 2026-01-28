package com.englishforit.backend.service;

import com.englishforit.backend.model.User;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PracticeService {

  private final ChatClient chatClient;

  public PracticeService(ChatClient chatClient) {
    this.chatClient = chatClient;
  }

  /**
   * Generates a personalized quiz for the user based on a specific topic or weak
   * skill.
   * 
   * @param user  The user profile
   * @param topic The topic to generate questions for (e.g. "Present Perfect",
   *              "Negotiation Vocabulary")
   * @return JSON string containing the quiz
   */
  public String generateQuiz(User user, String topic) {
    String systemText = """
        You are an expert English Teacher creating a quiz for a student.

        Student Profile:
        Role: {job_role}
        Level: {level}

        Topic: {topic}

        Generate 5 multiple-choice questions (A, B, C, D) to test their understanding of this topic.
        The context of the questions should be relevant to their job role ({job_role}).

        Output Strictly valid JSON in the following format:
        [
          \\{
            "id": 1,
            "question": "...",
            "options": \\{"A": "...", "B": "...", "C": "...", "D": "...\\},
            "correctAnswer": "B",
            "explanation": "..."
          \\}
        ]
        """;

    PromptTemplate template = new PromptTemplate(systemText);
    Prompt prompt = template.create(Map.of(
        "job_role", user.getJobRole() != null ? user.getJobRole() : "IT Professional",
        "level", user.getCurrentLevel() != null ? user.getCurrentLevel() : "Intermediate",
        "topic", topic));

    return chatClient.call(prompt).getResult().getOutput().getContent();
  }
}
