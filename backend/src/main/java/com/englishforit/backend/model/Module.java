package com.englishforit.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.UUID;
import java.util.List;

@Entity
@Table(name = "modules")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Module {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private int moduleNumber; // 1 to 6

    @Column(nullable = false)
    private String title; // "Soft Skills", etc.

    @OneToMany(mappedBy = "module", cascade = CascadeType.ALL)
    private List<Week> weeks;
}
