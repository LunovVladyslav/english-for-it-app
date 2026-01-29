package com.englishforit.backend.service;

import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class TutorService {

    private final ChatClient chatClient;
    private final DocumentService documentService;

    public TutorService(ChatClient chatClient, DocumentService documentService) {
        this.chatClient = chatClient;
        this.documentService = documentService;
    }

    /**
     * Generates a response from the AI Tutor based on the current context and User
     * Profile.
     */
    public String getTutorResponse(com.englishforit.backend.model.User user, String userMessage, String context) {
        // Retrieve relevant documents
        var similarDocuments = documentService.search(userMessage);
        String ragContext = similarDocuments.stream()
                .map(org.springframework.ai.document.Document::getContent)
                .reduce("", (a, b) -> a + "\n" + b);

        String userProfile = String.format(
                "User Role: %s. Level: %s. Native Language: %s. Goal: %s.",
                user.getJobRole() != null ? user.getJobRole() : "IT Professional",
                user.getCurrentLevel() != null ? user.getCurrentLevel() : "Intermediate",
                user.getNativeLanguage() != null ? user.getNativeLanguage() : "Ukrainian",
                user.getPrimaryGoal() != null ? user.getPrimaryGoal() : "General Business English");

        String systemText = """
                You are an expert English Tutor for IT Professionals.

                Student Profile:
                {user_profile}

                Relevant Knowledge Base:
                {rag_context}

                Current Context: {context}

                User says: {message}
                """;

        PromptTemplate template = new PromptTemplate(systemText);
        Prompt prompt = template.create(Map.of(
                "user_profile", userProfile,
                "rag_context", ragContext,
                "context", context,
                "message", userMessage));

        return chatClient.call(prompt).getResult().getOutput().getContent();
    }
}
