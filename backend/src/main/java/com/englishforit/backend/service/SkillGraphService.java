package com.englishforit.backend.service;

import com.englishforit.backend.model.SkillNode;
import com.englishforit.backend.model.User;
import com.englishforit.backend.model.UserSkill;
import com.englishforit.backend.repository.SkillNodeRepository;
import com.englishforit.backend.repository.UserSkillRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SkillGraphService {

    private final SkillNodeRepository skillNodeRepository;
    private final UserSkillRepository userSkillRepository;

    public SkillGraphService(SkillNodeRepository skillNodeRepository, UserSkillRepository userSkillRepository) {
        this.skillNodeRepository = skillNodeRepository;
        this.userSkillRepository = userSkillRepository;
    }

    /**
     * Updates proficiency for a user on a specific skill.
     * If proficiency crosses a threshold (e.g., 80%), it might unlock children
     * (logic to be added).
     */
    @Transactional
    public UserSkill updateProficiency(User user, UUID skillNodeId, int score) {
        SkillNode node = skillNodeRepository.findById(skillNodeId)
                .orElseThrow(() -> new IllegalArgumentException("Skill Node not found"));

        Optional<UserSkill> existing = userSkillRepository.findByUserIdAndSkillNodeId(user.getId(), skillNodeId);

        UserSkill userSkill;
        if (existing.isPresent()) {
            userSkill = existing.get();
            // Simple logic: update if score is higher, or just set it.
            userSkill.setProficiencyLevel(Math.max(userSkill.getProficiencyLevel(), score));
        } else {
            userSkill = new UserSkill();
            userSkill.setUser(user);
            userSkill.setSkillNode(node);
            userSkill.setProficiencyLevel(score);
            userSkill.setUnlocked(true); // Assuming if you update it, you have access.
        }

        return userSkillRepository.save(userSkill);
    }

    /**
     * Returns a list of skills that are unlocked but not yet mastered (< 100%).
     */
    public List<UserSkill> getActiveSkills(UUID userId) {
        return userSkillRepository.findByUserId(userId).stream()
                .filter(us -> us.isUnlocked() && us.getProficiencyLevel() < 100)
                .collect(Collectors.toList());
    }
}
