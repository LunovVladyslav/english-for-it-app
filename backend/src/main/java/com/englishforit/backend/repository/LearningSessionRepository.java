package com.englishforit.backend.repository;

import com.englishforit.backend.model.LearningSession;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface LearningSessionRepository extends JpaRepository<LearningSession, UUID> {
    Optional<LearningSession> findTopByUserIdOrderByStartedAtDesc(UUID userId);
}
