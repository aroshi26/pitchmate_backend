package com.example.demo.services.Interfaces;

import com.example.demo.Entities.Achievement;
import com.example.demo.Entities.Enum.AchievementTitle;

import java.util.List;
import java.util.Optional;

public interface AchievementService {
    List<Achievement> getAllAchievements();
    Optional<Achievement> getAchievementById(Integer id);
    Achievement createAchievement(Integer matchId, Integer playerId, AchievementTitle title);
    Achievement updateAchievement(Integer id, Achievement achievement);
    void deleteAchievement(Integer id);

    // Add method to get achievements by player ID
    List<Achievement> getAchievementsByPlayerId(Integer playerId);
}
