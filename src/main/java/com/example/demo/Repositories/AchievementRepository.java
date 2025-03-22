package com.example.demo.Repositories;

import com.example.demo.Entities.Achievement;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AchievementRepository extends JpaRepository<Achievement, Integer> {
    // Corrected query method
    List<Achievement> findByPlayer_PlayerId(Integer playerId);  // Correct path for the player's id
}
