package com.englishforit.backend.repository;

import com.englishforit.backend.model.UserSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserSkillRepository extends JpaRepository<UserSkill, UUID> {
    Optional<UserSkill> findByUserIdAndSkillNodeId(UUID userId, UUID skillNodeId);
    List<UserSkill> findByUserId(UUID userId);
}
