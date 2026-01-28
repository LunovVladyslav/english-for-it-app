package com.englishforit.backend.service;

import com.englishforit.backend.model.Day;
import com.englishforit.backend.model.Lesson;
import com.englishforit.backend.model.Module;
import com.englishforit.backend.model.Week;
import com.englishforit.backend.repository.DayRepository;
import com.englishforit.backend.repository.LessonRepository;
import com.englishforit.backend.repository.ModuleRepository;
import com.englishforit.backend.repository.WeekRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ContentIngestionService {

    private final ModuleRepository moduleRepository;
    private final WeekRepository weekRepository;
    private final DayRepository dayRepository;
    private final LessonRepository lessonRepository;
    private final org.springframework.core.io.support.ResourcePatternResolver resourcePatternResolver;

    public ContentIngestionService(ModuleRepository moduleRepository, WeekRepository weekRepository,
            DayRepository dayRepository, LessonRepository lessonRepository,
            org.springframework.core.io.support.ResourcePatternResolver resourcePatternResolver) {
        this.moduleRepository = moduleRepository;
        this.weekRepository = weekRepository;
        this.dayRepository = dayRepository;
        this.lessonRepository = lessonRepository;
        this.resourcePatternResolver = resourcePatternResolver;
    }

    @Transactional
    public void ingestContent() {
        try {
            org.springframework.core.io.Resource[] resources = resourcePatternResolver
                    .getResources("classpath:content/*_DETAILED.md");

            for (org.springframework.core.io.Resource resource : resources) {
                parseAndSaveFile(resource);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void parseAndSaveFile(org.springframework.core.io.Resource resource) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            String line;
            Module currentModule = null;
            Week currentWeek = null;
            Day currentDay = null;
            Lesson currentLesson = null;
            StringBuilder contentBuffer = new StringBuilder();

            Pattern modulePattern = Pattern.compile("^# MODULE (\\d+): (.*)");
            Pattern weekPattern = Pattern.compile("^# ТИЖДЕНЬ (\\d+): (.*)|^# WEEK (\\d+): (.*)");
            Pattern dayPattern = Pattern.compile("^## (DAY|ДЕНЬ) (\\d+): (.*)");
            Pattern lessonPattern = Pattern.compile("^### (LESSON|УРОК) (\\d+\\.\\d+): (.*)");

            while ((line = br.readLine()) != null) {
                Matcher moduleMatcher = modulePattern.matcher(line);
                Matcher weekMatcher = weekPattern.matcher(line);
                Matcher dayMatcher = dayPattern.matcher(line);
                Matcher lessonMatcher = lessonPattern.matcher(line);

                if (moduleMatcher.find()) {
                    if (currentModule == null) {
                        int modNum = Integer.parseInt(moduleMatcher.group(1));
                        String title = moduleMatcher.group(2);

                        currentModule = moduleRepository.findAll().stream()
                                .filter(m -> m.getModuleNumber() == modNum)
                                .findFirst()
                                .orElse(new Module());

                        currentModule.setModuleNumber(modNum);
                        currentModule.setTitle(title);
                        currentModule = moduleRepository.save(currentModule);
                    }
                } else if (weekMatcher.find()) {
                    int weekNum = Integer
                            .parseInt(weekMatcher.group(1) != null ? weekMatcher.group(1) : weekMatcher.group(3));
                    String title = weekMatcher.group(2) != null ? weekMatcher.group(2) : weekMatcher.group(4);

                    if (currentModule != null) {
                        currentWeek = weekRepository.findByModuleAndWeekNumber(currentModule, weekNum)
                                .orElse(new Week());
                        currentWeek.setModule(currentModule);
                        currentWeek.setWeekNumber(weekNum);
                        currentWeek.setTitle(title);
                        currentWeek = weekRepository.save(currentWeek);
                    }
                } else if (dayMatcher.find()) {
                    int dayNum = Integer.parseInt(dayMatcher.group(2));
                    String title = dayMatcher.group(3);

                    if (currentWeek != null) {
                        currentDay = dayRepository.findByWeekAndDayNumber(currentWeek, dayNum)
                                .orElse(new Day());
                        currentDay.setWeek(currentWeek);
                        currentDay.setDayNumber(dayNum);
                        currentDay.setTitle(title);
                        currentDay = dayRepository.save(currentDay);
                    }
                } else if (lessonMatcher.find()) {
                    if (currentLesson != null) {
                        currentLesson.setContent(contentBuffer.toString());
                        lessonRepository.save(currentLesson);
                        contentBuffer.setLength(0);
                    }

                    String title = lessonMatcher.group(3);

                    if (currentDay != null) {
                        currentLesson = lessonRepository.findByDayAndTitle(currentDay, title)
                                .orElse(new Lesson());
                        currentLesson.setDay(currentDay);
                        currentLesson.setTitle(title);
                        currentLesson.setType("LESSON");
                    }
                } else {
                    if (currentLesson != null) {
                        contentBuffer.append(line).append("\n");
                    }
                }
            }

            if (currentLesson != null) {
                currentLesson.setContent(contentBuffer.toString());
                lessonRepository.save(currentLesson);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
