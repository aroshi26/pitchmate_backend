package com.example.demo.Controllers;

import com.example.demo.Entities.PlayerPerformance;
import com.example.demo.Entities.PlayerPerformanceId;
import com.example.demo.Repositories.PlayerPerformanceRepository;
import com.example.demo.dto.PlayerAggregatedStats;
import com.example.demo.services.Interfaces.PlayerPerformanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/player-performances")
public class PlayerPerformanceController {

    @Autowired
    private PlayerPerformanceService playerPerformanceService;

    @Autowired
    private PlayerPerformanceRepository playerPerformanceRepository; // ✅ FIXED: Properly autowired repository

    @GetMapping
    public ResponseEntity<List<PlayerPerformance>> getAllPlayerPerformances() {
        return ResponseEntity.ok(playerPerformanceService.getAllPlayerPerformances());
    }

    @GetMapping("/aggregated/{playerId}")
    public ResponseEntity<PlayerAggregatedStats> getAggregatedStats(@PathVariable Integer playerId) {
        PlayerAggregatedStats stats = playerPerformanceService.getAggregatedStatsByPlayerId(playerId);
        if (stats == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(stats);
    }



    @GetMapping("/{playerId}/{matchId}")
    public ResponseEntity<PlayerPerformance> getPlayerPerformanceById(
            @PathVariable Integer playerId, @PathVariable Integer matchId) {
        PlayerPerformanceId id = new PlayerPerformanceId(playerId, matchId);
        return playerPerformanceService.getPlayerPerformanceById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @PostMapping
    public ResponseEntity<PlayerPerformance> createPlayerPerformance(@RequestBody PlayerPerformance performance) {
        return ResponseEntity.ok(playerPerformanceService.createPlayerPerformance(performance));
    }


    @PutMapping("/{playerId}/{matchId}")
    public ResponseEntity<PlayerPerformance> updatePlayerPerformance(
            @PathVariable Integer playerId,
            @PathVariable Integer matchId,
            @RequestBody PlayerPerformance updatedData) {

        PlayerPerformanceId id = new PlayerPerformanceId(playerId, matchId);
        PlayerPerformance updatedPerformance = playerPerformanceService.updatePlayerPerformance(id, updatedData);
        return ResponseEntity.ok(updatedPerformance);
    }

    @DeleteMapping("/{playerId}/{matchId}")
    public ResponseEntity<Void> deletePlayerPerformance(
            @PathVariable Integer playerId, @PathVariable Integer matchId) {
        PlayerPerformanceId id = new PlayerPerformanceId(playerId, matchId);
        playerPerformanceService.deletePlayerPerformance(id);
        return ResponseEntity.noContent().build();
    }
}
