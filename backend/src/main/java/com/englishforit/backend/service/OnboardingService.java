package com.englishforit.backend.service;

import com.englishforit.backend.model.User;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class OnboardingService {

        private final ChatClient chatClient;

        public OnboardingService(ChatClient chatClient) {
                this.chatClient = chatClient;
        }

        /**
         * Starts the assessment by generating an initial greeting and the first
         * question.
         */
        public String startAssessment(User user) {
                String systemText = """
                                You are a professional English Assessor for IT professionals.
                                Your goal is to determine the user's CEFR level (A1, A2, B1, B2, C1, C2).

                                The user is a: {job_role}.
                                Native Language: {native_language}.

                                Start by greeting them professionally and asking a relevant open-ended question about their work experience
                                that would require them to use a mix of tenses or technical vocabulary.
                                Do not mention you are an AI. Act as a human interviewer.
                                """;

                PromptTemplate template = new PromptTemplate(systemText);
                Prompt prompt = template.create(Map.of(
                                "job_role", user.getJobRole() != null ? user.getJobRole() : "IT Professional",
                                "native_language",
                                user.getNativeLanguage() != null ? user.getNativeLanguage() : "Unknown"));

                return chatClient.call(prompt).getResult().getOutput().getContent();
        }

        /**
         * Continues the assessment chat.
         * In a real app, 'history' would be stored in a Vector DB or Session.
         * Here we expect the client to pass the context or simple previous message for
         * simplicity,
         * or we just treat it as single-turn for this MVP (which is suboptimal but fits
         * constraints).
         * 
         * IMPROVEMENT: Let's assume we rely on the prompt to "continue" based on last
         * user input.
         */
        public String chatAssessment(User user, String userMessage, String conversationHistory) {
                String systemText = """
                                You are a professional English Assessor.
                                User Role: {job_role}.

                                Conversation History:
                                {history}

                                User just said: "{message}"

                                Analyze their response implicitly.
                                If you have enough information to determine their level (usually after 3-4 turns),
                                say "THANK_YOU_ASSESSMENT_COMPLETE" and nothing else.

                                Otherwise, ask a follow-up question digging deeper into a technical topic or soft skill scenario
                                (e.g., handling conflict, explaining architecture) to test their limits.
                                """;

                PromptTemplate template = new PromptTemplate(systemText);
                Prompt prompt = template.create(Map.of(
                                "job_role", user.getJobRole() != null ? user.getJobRole() : "IT Professional",
                                "history", conversationHistory,
                                "message", userMessage));

                return chatClient.call(prompt).getResult().getOutput().getContent();
        }

        /**
         * Finalizes the assessment and returns a structured analysis.
         */
        public String finalizeAssessment(User user, String fullConversationHistory) {
                String systemText = """
                                Analyze the following assessment interview for an IT Professional ({job_role}).

                                Conversation:
                                {history}

                                Determine:
                                1. CEFR Level (A1-C2).
                                2. Key Grammar Weaknesses.
                                3. Key Vocabulary Gaps.
                                4. Recommended Learning Focus.

                                Output ONLY valid JSON in this format:
                                \\{
                                  "level": "B2",
                                  "strengths": "...",
                                  "weaknesses": "...",
                                  "focus": "..."
                                \\}
                                """;

                PromptTemplate template = new PromptTemplate(systemText);
                Prompt prompt = template.create(Map.of(
                                "job_role", user.getJobRole() != null ? user.getJobRole() : "IT Professional",
                                "history", fullConversationHistory));

                return chatClient.call(prompt).getResult().getOutput().getContent();
        }
}
