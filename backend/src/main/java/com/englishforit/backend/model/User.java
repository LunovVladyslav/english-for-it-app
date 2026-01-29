package com.englishforit.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.UUID;
import java.time.LocalDateTime;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

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
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @Column(nullable = false)
    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;

    private int xp = 0;
    private int level = 1;
    private int currentStreak = 0;
    private java.time.LocalDate lastLoginDate;

    @Column(nullable = false)
    @NotBlank(message = "Name is required")
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
