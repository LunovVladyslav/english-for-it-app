package com.englishforit.backend.controller;

import com.englishforit.backend.config.LearningEvent;
import com.englishforit.backend.model.LearningSession;
import com.englishforit.backend.model.User;
import com.englishforit.backend.repository.UserRepository;
import com.englishforit.backend.service.LearningFlowService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/learning")
public class LearningController {

    private final LearningFlowService learningFlowService;
    private final UserRepository userRepository;
    private final com.englishforit.backend.service.ModuleService moduleService;

    public LearningController(LearningFlowService learningFlowService, UserRepository userRepository,
            com.englishforit.backend.service.ModuleService moduleService) {
        this.learningFlowService = learningFlowService;
        this.userRepository = userRepository;
        this.moduleService = moduleService;
    }

    @PostMapping("/start")
    public ResponseEntity<LearningSession> startSession(@RequestParam UUID userId) {
        if (userId == null) {
            return ResponseEntity.badRequest().build();
        }
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return ResponseEntity.ok(learningFlowService.startSession(user));
    }

    @PostMapping("/event")
    public ResponseEntity<String> triggerEvent(@RequestParam UUID sessionId, @RequestParam LearningEvent event) {
        boolean accepted = learningFlowService.sendEvent(sessionId, event);
        if (accepted) {
            return ResponseEntity.ok("Event accepted, transition triggered.");
        } else {
            return ResponseEntity.badRequest().body("Event rejected in current state.");
        }
    }

    @GetMapping("/modules/{moduleId}/export")
    public ResponseEntity<com.englishforit.backend.model.Module> exportModule(@PathVariable UUID moduleId) {
        return ResponseEntity.ok(moduleService.getModuleExport(moduleId));
    }

    @GetMapping("/modules")
    public ResponseEntity<java.util.List<com.englishforit.backend.model.Module>> getModules() {
        return ResponseEntity.ok(moduleService.getAllModules());
    }
}
