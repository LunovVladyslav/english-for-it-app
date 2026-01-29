package com.englishforit.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.UUID;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private int xp = 0;
    private int level = 1;
    private int currentStreak = 0;
    private java.time.LocalDate lastLoginDate;

    @Column(nullable = false)
    private String name;

    @Column(name = "current_level")
    private String currentLevel; // e.g., "B1", "B2"

    @Column(name = "job_role")
    private String jobRole; // e.g. "Senior Java Developer"

    @Column(name = "native_language")
    private String nativeLanguage; // e.g. "Ukrainian"

    @Column(name = "primary_goal")
    private String primaryGoal; // e.g. "Improve negotiation skills"

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}
