package com.example.demo.services;

import com.example.demo.Entities.Achievement;
import com.example.demo.Entities.Enum.AchievementTitle;
import com.example.demo.Entities.Match;
import com.example.demo.Entities.Player;
import com.example.demo.Repositories.AchievementRepository;
import com.example.demo.Repositories.MatchRepository;
import com.example.demo.Repositories.PlayerRepository;
import com.example.demo.services.Interfaces.AchievementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AchievementServiceImpl implements AchievementService {


    @Autowired
    private AchievementRepository achievementRepository;

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Override
    public List<Achievement> getAllAchievements() {
        return achievementRepository.findAll();
    }

    @Override
    public Optional<Achievement> getAchievementById(Integer id) {
        return achievementRepository.findById(id);
    }

    @Override
    public Achievement createAchievement(Integer matchId, Integer playerId, AchievementTitle title) {
        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new RuntimeException("Match not found"));

        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new RuntimeException("Player not found"));

        Achievement achievement = new Achievement(match, player, title);
        return achievementRepository.save(achievement);
    }
    @Override
    public Achievement updateAchievement(Integer id, Achievement achievement) {
        return achievementRepository.findById(id).map(existing -> {
            existing.setMatch(achievement.getMatch());
            existing.setPlayer(achievement.getPlayer());
            existing.setTitle(achievement.getTitle());
            return achievementRepository.save(existing);
        }).orElseThrow(() -> new RuntimeException("Achievement not found"));
    }

    @Override
    public void deleteAchievement(Integer id) {
        achievementRepository.deleteById(id);
    }
    // Add method to get achievements by player ID
    @Override
    public List<Achievement> getAchievementsByPlayerId(Integer playerId) {
        return achievementRepository.findByPlayer_PlayerId(playerId);
    }
}
