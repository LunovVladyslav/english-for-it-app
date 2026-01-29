package com.englishforit.backend.controller;

import com.englishforit.backend.config.LearningEvent;
import com.englishforit.backend.model.LearningSession;
import com.englishforit.backend.model.Module;
import com.englishforit.backend.model.User;
import com.englishforit.backend.repository.UserRepository;
import com.englishforit.backend.service.LearningFlowService;
import com.englishforit.backend.service.ModuleService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LearningControllerTest {

    @Mock
    private LearningFlowService learningFlowService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModuleService moduleService;

    @InjectMocks
    private LearningController learningController;

    @Test
    void startSession_ShouldReturnSession_WhenUserExists() {
        UUID userId = UUID.randomUUID();
        User user = new User();
        user.setId(userId);
        LearningSession session = new LearningSession();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(learningFlowService.startSession(user)).thenReturn(session);

        ResponseEntity<LearningSession> response = learningController.startSession(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void exportModule_ShouldReturnModule() {
        UUID moduleId = UUID.randomUUID();
        Module module = new Module();
        module.setId(moduleId);

        when(moduleService.getModuleExport(moduleId)).thenReturn(module);

        ResponseEntity<Module> response = learningController.exportModule(moduleId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(moduleId, response.getBody().getId());
    }

    @Test
    void getModules_ShouldReturnList() {
        Module module = new Module();
        when(moduleService.getAllModules()).thenReturn(Collections.singletonList(module));

        ResponseEntity<List<Module>> response = learningController.getModules();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void triggerEvent_ShouldReturnOk_WhenAccepted() {
        UUID sessionId = UUID.randomUUID();
        LearningEvent event = LearningEvent.PRACTICE_COMPLETE;

        when(learningFlowService.sendEvent(sessionId, event)).thenReturn(true);

        ResponseEntity<String> response = learningController.triggerEvent(sessionId, event);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Event accepted, transition triggered.", response.getBody());
    }
}
