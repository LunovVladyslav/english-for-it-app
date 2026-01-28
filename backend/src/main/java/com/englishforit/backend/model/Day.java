package com.englishforit.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.UUID;
import java.util.List;

@Entity
@Table(name = "days")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Day {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "week_id", nullable = false)
    private Week week;

    @Column(nullable = false)
    private int dayNumber; // Overall course day (1-168) or week day (1-7)

    @Column(nullable = false)
    private String title;

    @OneToMany(mappedBy = "day", cascade = CascadeType.ALL)
    private List<Lesson> lessons;
}
