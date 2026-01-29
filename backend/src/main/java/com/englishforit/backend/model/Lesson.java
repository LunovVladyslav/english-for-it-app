package com.englishforit.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.UUID;

@Entity
@Table(name = "lessons")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "day_id", nullable = false)
    @jakarta.validation.constraints.NotNull(message = "Day is required")
    @com.fasterxml.jackson.annotation.JsonIgnore
    private Day day;

    @Column(nullable = false)
    @jakarta.validation.constraints.NotBlank(message = "Title is required")
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content; // Stored as markdown or JSON structure

    @Column(nullable = false)
    @jakarta.validation.constraints.NotBlank(message = "Type is required")
    private String type; // VOCABULARY, GRAMMAR, QUIZ, TASK
}
