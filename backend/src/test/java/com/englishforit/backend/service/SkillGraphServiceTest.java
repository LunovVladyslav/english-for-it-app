package com.englishforit.backend.service;

import com.englishforit.backend.model.SkillNode;
import com.englishforit.backend.model.User;
import com.englishforit.backend.model.UserSkill;
import com.englishforit.backend.repository.SkillNodeRepository;
import com.englishforit.backend.repository.UserSkillRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SkillGraphServiceTest {

    @Mock
    private SkillNodeRepository skillNodeRepository;

    @Mock
    private UserSkillRepository userSkillRepository;

    @InjectMocks
    private SkillGraphService skillGraphService;

    @Test
    void updateProficiency_ShouldCreateNewUserSkill_WhenNotExists() {
        // Arrange
        User user = new User();
        user.setId(UUID.randomUUID());

        SkillNode node = new SkillNode();
        node.setId(UUID.randomUUID());
        node.setName("Test Skill");

        when(skillNodeRepository.findById(node.getId())).thenReturn(Optional.of(node));
        when(userSkillRepository.findByUserIdAndSkillNodeId(user.getId(), node.getId())).thenReturn(Optional.empty());
        when(userSkillRepository.save(any(UserSkill.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        skillGraphService.updateProficiency(user, node.getId(), 50);

        // Assert
        verify(userSkillRepository).save(any(UserSkill.class));
    }

    @Test
    void updateProficiency_ShouldUpdateExisting_WhenExists() {
        // Arrange
        User user = new User();
        user.setId(UUID.randomUUID());

        SkillNode node = new SkillNode();
        node.setId(UUID.randomUUID());

        UserSkill existingSkill = new UserSkill();
        existingSkill.setProficiencyLevel(20);

        when(skillNodeRepository.findById(node.getId())).thenReturn(Optional.of(node));
        when(userSkillRepository.findByUserIdAndSkillNodeId(user.getId(), node.getId()))
                .thenReturn(Optional.of(existingSkill));
        when(userSkillRepository.save(any(UserSkill.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        skillGraphService.updateProficiency(user, node.getId(), 80);

        // Assert
        assertEquals(80, existingSkill.getProficiencyLevel());
        verify(userSkillRepository).save(existingSkill);
    }
}
