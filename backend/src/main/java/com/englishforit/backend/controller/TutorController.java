package com.englishforit.backend.controller;

import com.englishforit.backend.service.TutorService;
import com.englishforit.backend.repository.UserRepository;
import com.englishforit.backend.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/tutor")
public class TutorController {

    private final TutorService tutorService;
    private final UserRepository userRepository;

    public TutorController(TutorService tutorService, UserRepository userRepository) {
        this.tutorService = tutorService;
        this.userRepository = userRepository;
    }

    @PostMapping("/chat")
    public ResponseEntity<String> chat(@RequestBody Map<String, String> payload) {
        String userIdStr = payload.get("userId");
        if (userIdStr == null)
            return ResponseEntity.badRequest().body("userId is required");

        UUID userId;
        try {
            userId = UUID.fromString(userIdStr);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid UUID");
        }

        String message = payload.get("message");
        String context = payload.getOrDefault("context", "General English");

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        String response = tutorService.getTutorResponse(user, message, context);
        return ResponseEntity.ok(response);
    }
}
