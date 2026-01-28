package com.englishforit.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.UUID;
import java.util.List;

@Entity
@Table(name = "skill_nodes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SkillNode {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name; // e.g., "Past Simple", "Negotiation"

    @Column(nullable = false)
    private String category; // e.g., "GRAMMAR", "VOCABULARY", "SOFT_SKILL"

    @Column(columnDefinition = "TEXT")
    private String description;

    // Adjacency list for the Graph structure
    @ManyToMany
    @JoinTable(name = "skill_dependencies", joinColumns = @JoinColumn(name = "child_id"), inverseJoinColumns = @JoinColumn(name = "parent_id"))
    private List<SkillNode> parents;
}
