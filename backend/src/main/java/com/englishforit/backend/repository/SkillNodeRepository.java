package com.englishforit.backend.repository;

import com.englishforit.backend.model.SkillNode;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface SkillNodeRepository extends JpaRepository<SkillNode, UUID> {
}
