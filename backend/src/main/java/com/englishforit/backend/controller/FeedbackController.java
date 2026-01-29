package com.englishforit.backend.controller;

import com.englishforit.backend.service.EmailService;
import com.englishforit.backend.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import java.util.Map;

@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {

    private final EmailService emailService;
    private final UserRepository userRepository;

    public FeedbackController(EmailService emailService, UserRepository userRepository) {
        this.emailService = emailService;
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity<String> submitFeedback(@RequestBody Map<String, String> payload,
            Authentication authentication) {
        String username = (authentication != null) ? authentication.getName() : "Anonymous";
        String messageIs = payload.get("message");
        String type = payload.getOrDefault("type", "General");

        if (messageIs == null || messageIs.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Message is required");
        }

        emailService.sendFeedbackEmail(username, type, messageIs);

        return ResponseEntity.ok("Feedback submitted successfully");
    }
}
