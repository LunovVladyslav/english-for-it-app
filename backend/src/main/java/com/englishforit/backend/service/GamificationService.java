package com.englishforit.backend.service;

import com.englishforit.backend.model.User;
import com.englishforit.backend.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
public class GamificationService {

    private final UserRepository userRepository;

    public GamificationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void updateStreak(User user) {
        LocalDate today = LocalDate.now();
        LocalDate lastLogin = user.getLastLoginDate();

        if (lastLogin == null) {
            user.setCurrentStreak(1);
        } else if (lastLogin.equals(today.minusDays(1))) {
            user.setCurrentStreak(user.getCurrentStreak() + 1);
        } else if (lastLogin.isBefore(today.minusDays(1))) {
            user.setCurrentStreak(1); // Reset streak if missed a day
        }
        // If lastLogin is today, do nothing (streak already counted)

        user.setLastLoginDate(today);
        userRepository.save(user);
    }

    public void awardXp(User user, int amount) {
        user.setXp(user.getXp() + amount);

        // Simple level up logic: Level = 1 + (XP / 100)
        int newLevel = 1 + (user.getXp() / 100);
        if (newLevel > user.getLevel()) {
            user.setLevel(newLevel);
            // Could add notification logic here
        }

        userRepository.save(user);
    }
}
