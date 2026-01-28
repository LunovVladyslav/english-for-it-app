package com.englishforit.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.UUID;
import java.util.List;

@Entity
@Table(name = "weeks")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Week {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "module_id", nullable = false)
    private Module module;

    @Column(nullable = false)
    private int weekNumber; // 1 to 4

    @Column(nullable = false)
    private String title;

    @OneToMany(mappedBy = "week", cascade = CascadeType.ALL)
    private List<Day> days;
}
