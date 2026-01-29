package com.englishforit.backend.controller;

import com.englishforit.backend.model.User;
import com.englishforit.backend.repository.UserRepository;
import com.englishforit.backend.service.GamificationService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/gamification")
public class GamificationController {

    private final GamificationService gamificationService;
    private final UserRepository userRepository;

    public GamificationController(GamificationService gamificationService, UserRepository userRepository) {
        this.gamificationService = gamificationService;
        this.userRepository = userRepository;
    }

    @PostMapping("/xp")
    public User awardXp(@RequestBody Map<String, Integer> payload, Authentication authentication) {
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        int amount = payload.getOrDefault("amount", 10);
        gamificationService.awardXp(user, amount);

        return user;
    }
}
