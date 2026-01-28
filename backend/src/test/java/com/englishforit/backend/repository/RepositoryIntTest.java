package com.englishforit.backend.repository;

import com.englishforit.backend.AbstractIntegrationTest;
import com.englishforit.backend.model.Day;
import com.englishforit.backend.model.Lesson;
import com.englishforit.backend.model.Module;
import com.englishforit.backend.model.Week;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RepositoryIntTest extends AbstractIntegrationTest {

    @Autowired
    private ModuleRepository moduleRepository;
    @Autowired
    private WeekRepository weekRepository;
    @Autowired
    private DayRepository dayRepository;
    @Autowired
    private LessonRepository lessonRepository;

    @Test
    @Transactional
    void shouldFindEntitiesByHierarchy() {
        // 1. Setup Data hierarchy
        Module module = new Module();
        module.setModuleNumber(99);
        module.setTitle("Integration Test Module");
        module = moduleRepository.save(module);

        Week week = new Week();
        week.setModule(module);
        week.setWeekNumber(1);
        week.setTitle("Week 1 Test");
        week = weekRepository.save(week);

        Day day = new Day();
        day.setWeek(week);
        day.setDayNumber(1);
        day.setTitle("Day 1 Test");
        day = dayRepository.save(day);

        Lesson lesson = new Lesson();
        lesson.setDay(day);
        lesson.setTitle("Lesson 1 Test");
        lesson.setType("LESSON");
        lesson.setContent("Content");
        lesson = lessonRepository.save(lesson);

        // 2. Test WeekRepository custom finder
        Optional<Week> foundWeek = weekRepository.findByModuleAndWeekNumber(module, 1);
        assertTrue(foundWeek.isPresent());
        assertEquals("Week 1 Test", foundWeek.get().getTitle());

        // 3. Test DayRepository custom finder
        Optional<Day> foundDay = dayRepository.findByWeekAndDayNumber(week, 1);
        assertTrue(foundDay.isPresent());
        assertEquals("Day 1 Test", foundDay.get().getTitle());

        // 4. Test LessonRepository custom finder
        Optional<Lesson> foundLesson = lessonRepository.findByDayAndTitle(day, "Lesson 1 Test");
        assertTrue(foundLesson.isPresent());
        assertEquals("Lesson 1 Test", foundLesson.get().getTitle());
    }
}
