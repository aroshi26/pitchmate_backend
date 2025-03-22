package com.example.demo.Controllers;

import com.example.demo.Entities.Achievement;

import com.example.demo.Entities.Enum.AchievementTitle;
import com.example.demo.Repositories.AchievementRepository;
import com.example.demo.services.Interfaces.AchievementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/achievements")
public class AchievementController {

    @Autowired
    private AchievementService achievementService;
    private AchievementRepository achievementRepository;

    @GetMapping
    public ResponseEntity<List<Achievement>> getAllAchievements() {
        return ResponseEntity.ok(achievementService.getAllAchievements());
    }

    @GetMapping("/{playerId}")
    public ResponseEntity<List<Achievement>> getAchievementsByPlayerId(@PathVariable Integer playerId) {
        List<Achievement> achievements = achievementService.getAchievementsByPlayerId(playerId);
        return achievements.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(achievements);
    }

    @PostMapping
    public ResponseEntity<?> createAchievement(@RequestBody Map<String, Object> requestData) {
        try {
            Integer matchId = (Integer) requestData.get("matchId");
            Integer playerId = (Integer) requestData.get("playerId");
            String titleStr = (String) requestData.get("title");

            AchievementTitle title = AchievementTitle.valueOf(titleStr); // Convert String to Enum

            Achievement savedAchievement = achievementService.createAchievement(matchId, playerId, title);
            return ResponseEntity.ok(savedAchievement);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid Achievement Title");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error saving achievement: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Achievement> updateAchievement(@PathVariable Integer id, @RequestBody Achievement achievement) {
        return ResponseEntity.ok(achievementService.updateAchievement(id, achievement));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAchievement(@PathVariable Integer id) {
        achievementService.deleteAchievement(id);
        return ResponseEntity.noContent().build();
    }
}
