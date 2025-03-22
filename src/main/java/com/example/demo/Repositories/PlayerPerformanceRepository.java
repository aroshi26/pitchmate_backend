package com.example.demo.Repositories;

import com.example.demo.Entities.PlayerPerformance;
import com.example.demo.Entities.PlayerPerformanceId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlayerPerformanceRepository extends JpaRepository<PlayerPerformance, PlayerPerformanceId> {

    // Fetch performance for a specific player in a specific match
    Optional<PlayerPerformance> findById(PlayerPerformanceId id);

    // Fetch all performances for a given player across matches
    List<PlayerPerformance> findByIdPlayerId(int playerId);

    // Fetch all performances for a specific match
    List<PlayerPerformance> findByIdMatchId(int matchId);
}
