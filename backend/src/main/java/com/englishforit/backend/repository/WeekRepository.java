package com.englishforit.backend.repository;

import com.englishforit.backend.model.Module;
import com.englishforit.backend.model.Week;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface WeekRepository extends JpaRepository<Week, UUID> {
    java.util.Optional<Week> findByModuleAndWeekNumber(Module module, int weekNumber);
}
