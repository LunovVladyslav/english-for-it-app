package com.englishforit.backend.repository;

import com.englishforit.backend.model.Day;
import com.englishforit.backend.model.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface LessonRepository extends JpaRepository<Lesson, UUID> {
    java.util.Optional<Lesson> findByDayAndTitle(Day day, String title);
}
