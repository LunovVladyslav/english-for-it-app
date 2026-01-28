package com.englishforit.backend.controller;

import com.englishforit.backend.model.User;
import com.englishforit.backend.repository.UserRepository;
import com.englishforit.backend.service.PracticeService;
import com.englishforit.backend.service.RoleplayService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/practice")
public class PracticeController {

    private final PracticeService practiceService;
    private final RoleplayService roleplayService;
    private final UserRepository userRepository;

    public PracticeController(PracticeService practiceService, RoleplayService roleplayService,
            UserRepository userRepository) {
        this.practiceService = practiceService;
        this.roleplayService = roleplayService;
        this.userRepository = userRepository;
    }

    // --- Quiz Endpoints ---

    @PostMapping("/quiz/generate")
    public ResponseEntity<String> generateQuiz(@RequestBody Map<String, String> payload) {
        String userIdStr = payload.get("userId");
        if (userIdStr == null)
            return ResponseEntity.badRequest().body("userId is required");
        UUID userId = UUID.fromString(userIdStr);
        String topic = payload.getOrDefault("topic", "General IT Vocabulary");

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // In a real app, we might look up the user's weakest skill node here if topic
        // is not provided
        String quizJson = practiceService.generateQuiz(user, topic);
        return ResponseEntity.ok(quizJson);
    }

    // --- Roleplay Endpoints ---

    @PostMapping("/roleplay/start")
    public ResponseEntity<String> startRoleplay(@RequestBody Map<String, String> payload) {
        String userIdStr = payload.get("userId");
        if (userIdStr == null)
            return ResponseEntity.badRequest().body("userId is required");
        UUID userId = UUID.fromString(userIdStr);

        String scenario = payload.getOrDefault("scenario", "Daily Standup");
        String aiPersona = payload.getOrDefault("aiPersona", "Scrum Master");

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        return ResponseEntity.ok(roleplayService.startRoleplay(user, scenario, aiPersona));
    }

    @PostMapping("/roleplay/chat")
    public ResponseEntity<String> chatRoleplay(@RequestBody Map<String, String> payload) {
        String userIdStr = payload.get("userId");
        if (userIdStr == null)
            return ResponseEntity.badRequest().body("userId is required");
        UUID userId = UUID.fromString(userIdStr);

        String message = payload.get("message");
        String history = payload.getOrDefault("history", "");
        String scenario = payload.getOrDefault("scenario", "General Work");
        String aiPersona = payload.getOrDefault("aiPersona", "Colleague");

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        return ResponseEntity.ok(roleplayService.chatRoleplay(user, message, history, scenario, aiPersona));
    }

    @PostMapping("/roleplay/feedback")
    public ResponseEntity<String> roleplayFeedback(@RequestBody Map<String, String> payload) {
        String userIdStr = payload.get("userId");
        if (userIdStr == null)
            return ResponseEntity.badRequest().body("userId is required");
        UUID userId = UUID.fromString(userIdStr);

        String history = payload.getOrDefault("history", "");

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        return ResponseEntity.ok(roleplayService.provideFeedback(user, history));
    }
}
