package com.englishforit.backend.service;

import com.englishforit.backend.model.User;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class RoleplayService {

    private final ChatClient chatClient;

    public RoleplayService(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    /**
     * initializes a roleplay scenario.
     */
    public String startRoleplay(User user, String scenario, String aiPersona) {
        String systemText = """
                You are acting as {ai_persona} in a roleplay scenario with an IT professional.

                Scenario: {scenario}
                User Role: {user_role}

                Start the conversation in character. Keep your response concise (1-2 sentences) to encourage dialogue.
                Do not break character.
                """;

        PromptTemplate template = new PromptTemplate(systemText);
        Prompt prompt = template.create(Map.of(
                "ai_persona", aiPersona,
                "scenario", scenario,
                "user_role", user.getJobRole() != null ? user.getJobRole() : "IT Professional"));

        return chatClient.call(prompt).getResult().getOutput().getContent();
    }

    /**
     * Continues the roleplay.
     */
    public String chatRoleplay(User user, String userMessage, String history, String scenario, String aiPersona) {
        String systemText = """
                You are acting as {ai_persona} in a roleplay with {user_role}.
                Scenario: {scenario}

                History:
                {history}

                User said: "{message}"

                Respond in character. Be reactive to what they said.
                If they make a mistake, do NOT correct them yet (stay in character).
                """;

        PromptTemplate template = new PromptTemplate(systemText);
        Prompt prompt = template.create(Map.of(
                "ai_persona", aiPersona,
                "user_role", user.getJobRole() != null ? user.getJobRole() : "IT Professional",
                "scenario", scenario,
                "history", history,
                "message", userMessage));

        return chatClient.call(prompt).getResult().getOutput().getContent();
    }

    /**
     * Generates feedback on the user's performance.
     */
    public String provideFeedback(User user, String history) {
        String systemText = """
                The roleplay session is finished. You are now the English Tutor.
                Analyze the user's performance in the following conversation:

                {history}

                Identify:
                1. What they did well (Soft skills & Language).
                2. Mistakes (Grammar, Vocabulary, Appropriateness).
                3. Suggestions for improvement.

                Be constructive and specific.
                """;

        PromptTemplate template = new PromptTemplate(systemText);
        Prompt prompt = template.create(Map.of(
                "history", history));

        return chatClient.call(prompt).getResult().getOutput().getContent();
    }
}
