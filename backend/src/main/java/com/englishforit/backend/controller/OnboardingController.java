package com.englishforit.backend.controller;

import com.englishforit.backend.model.User;
import com.englishforit.backend.repository.UserRepository;
import com.englishforit.backend.service.OnboardingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/onboarding")
public class OnboardingController {

    private final OnboardingService onboardingService;
    private final UserRepository userRepository;

    public OnboardingController(OnboardingService onboardingService, UserRepository userRepository) {
        this.onboardingService = onboardingService;
        this.userRepository = userRepository;
    }

    @PostMapping("/start")
    public ResponseEntity<String> startAssessment(@RequestParam UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return ResponseEntity.ok(onboardingService.startAssessment(user));
    }

    @PostMapping("/chat")
    public ResponseEntity<String> chatAssessment(@RequestBody Map<String, String> payload) {
        String userIdStr = payload.get("userId");
        if (userIdStr == null)
            return ResponseEntity.badRequest().body("userId is required");

        UUID userId = UUID.fromString(userIdStr);
        String message = payload.get("message");
        String history = payload.getOrDefault("history", ""); // Client should send accumulated history for this
                                                              // stateless MVP

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        return ResponseEntity.ok(onboardingService.chatAssessment(user, message, history));
    }

    @PostMapping("/finalize")
    public ResponseEntity<String> finalizeAssessment(@RequestBody Map<String, String> payload) {
        String userIdStr = payload.get("userId");
        if (userIdStr == null)
            return ResponseEntity.badRequest().body("userId is required");

        UUID userId = UUID.fromString(userIdStr);
        String history = payload.getOrDefault("history", "");

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        String analysisJson = onboardingService.finalizeAssessment(user, history);

        // In a real app, we would parse this JSON and update the User entity
        // (user.setCurrentLevel(...))
        // For now, we return the analysis to the frontend.

        return ResponseEntity.ok(analysisJson);
    }
}
