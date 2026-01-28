package com.englishforit.backend.service;

import com.englishforit.backend.config.LearningEvent;
import com.englishforit.backend.config.LearningState;
import com.englishforit.backend.model.LearningSession;
import com.englishforit.backend.model.User;
import com.englishforit.backend.repository.LearningSessionRepository;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class LearningFlowService {

    private final StateMachineFactory<LearningState, LearningEvent> stateMachineFactory;
    private final LearningSessionRepository learningSessionRepository;

    public LearningFlowService(StateMachineFactory<LearningState, LearningEvent> stateMachineFactory,
            LearningSessionRepository learningSessionRepository) {
        this.stateMachineFactory = stateMachineFactory;
        this.learningSessionRepository = learningSessionRepository;
    }

    @Transactional
    public LearningSession startSession(User user) {
        // Create a new session
        LearningSession session = new LearningSession();
        session.setUser(user);
        session.setCurrentState(LearningState.ASSESS.name());

        // In a real app, we would persist the SM Context or restore it.
        // For now, we assume a fresh start or simple tracking.
        return learningSessionRepository.save(session);
    }

    @Transactional
    public boolean sendEvent(UUID sessionId, LearningEvent event) {
        LearningSession session = learningSessionRepository.findById(sessionId)
                .orElseThrow(() -> new IllegalArgumentException("Session not found"));

        // Rehydrate SM (simplified)
        StateMachine<LearningState, LearningEvent> sm = stateMachineFactory.getStateMachine(sessionId.toString());
        sm.stop();

        // Only set initial state if needed, here we simplify assuming sequential events
        // match the persistent state for this demo.
        // sm.getStateMachineAccessor()... resetStateMachine(...)

        sm.start();
        boolean accepted = sm.sendEvent(event);

        if (accepted) {
            session.setCurrentState(sm.getState().getId().name());
            learningSessionRepository.save(session);
        }

        return accepted;
    }
}
