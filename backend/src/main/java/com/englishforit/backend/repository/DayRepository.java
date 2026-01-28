package com.englishforit.backend.repository;

import com.englishforit.backend.model.Week;
import com.englishforit.backend.model.Day;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface DayRepository extends JpaRepository<Day, UUID> {
    java.util.Optional<Day> findByWeekAndDayNumber(Week week, int dayNumber);
}
