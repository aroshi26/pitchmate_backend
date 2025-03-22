package com.example.demo.services.Interfaces;


import com.example.demo.Entities.PlayerPerformance;
import com.example.demo.Entities.PlayerPerformanceId;
import com.example.demo.dto.PlayerAggregatedStats;

import java.util.List;
import java.util.Optional;

public interface PlayerPerformanceService {
    List<PlayerPerformance> getAllPlayerPerformances();
    Optional<PlayerPerformance> getPlayerPerformanceById(PlayerPerformanceId id);
    PlayerPerformance createPlayerPerformance(PlayerPerformance performance);
    PlayerPerformance updatePlayerPerformance(PlayerPerformanceId id, PlayerPerformance performance);
    void deletePlayerPerformance(PlayerPerformanceId id);
    PlayerAggregatedStats getAggregatedStatsByPlayerId(Integer playerId);

}
