package com.example.demo.Controllers;

import com.example.demo.Entities.PlayerPerformance;
import com.example.demo.Entities.PlayerPerformanceId;
import com.example.demo.Repositories.PlayerPerformanceRepository;
import com.example.demo.dto.PlayerAggregatedStats;
import com.example.demo.services.Interfaces.PlayerPerformanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    // ✅ FIXED: `@PutMapping` method
    @PutMapping("/{playerId}/{matchId}")
    public ResponseEntity<PlayerPerformance> updatePlayerPerformance(
            @PathVariable Integer playerId,
            @PathVariable Integer matchId,
            @RequestBody PlayerPerformance updatedData) { // ✅ FIXED: Added `@RequestBody`

        PlayerPerformanceId id = new PlayerPerformanceId(playerId, matchId);
        Optional<PlayerPerformance> existingPerformance = playerPerformanceRepository.findById(id); // ✅ FIXED: Correct usage

        PlayerPerformance performance = existingPerformance.orElseGet(() -> new PlayerPerformance(id)); // Create new if not found

        // ✅ Only update non-null fields
        performance.setInnings(Optional.ofNullable(updatedData.getInnings()).orElse(performance.getInnings()));
        performance.setRunsScored(Optional.ofNullable(updatedData.getRunsScored()).orElse(performance.getRunsScored()));
        performance.setBallsFaced(Optional.ofNullable(updatedData.getBallsFaced()).orElse(performance.getBallsFaced()));
        performance.setHundreds(Optional.ofNullable(updatedData.getHundreds()).orElse(performance.getHundreds()));
        performance.setFifties(Optional.ofNullable(updatedData.getFifties()).orElse(performance.getFifties()));
        performance.setFours(Optional.ofNullable(updatedData.getFours()).orElse(performance.getFours()));
        performance.setSixes(Optional.ofNullable(updatedData.getSixes()).orElse(performance.getSixes()));
        performance.setDucks(Optional.ofNullable(updatedData.getDucks()).orElse(performance.getDucks()));
        performance.setDismissalsInvolved(Optional.ofNullable(updatedData.getDismissalsInvolved()).orElse(performance.getDismissalsInvolved()));
        performance.setOversBowled(Optional.ofNullable(updatedData.getOversBowled()).orElse(performance.getOversBowled()));
        performance.setBallsBowled(Optional.ofNullable(updatedData.getBallsBowled()).orElse(performance.getBallsBowled()));
        performance.setRunsConceded(Optional.ofNullable(updatedData.getRunsConceded()).orElse(performance.getRunsConceded()));
        performance.setWicketsTaken(Optional.ofNullable(updatedData.getWicketsTaken()).orElse(performance.getWicketsTaken()));
        performance.setFiveWicketHauls(Optional.ofNullable(updatedData.getFiveWicketHauls()).orElse(performance.getFiveWicketHauls()));
        performance.setMaidens(Optional.ofNullable(updatedData.getMaidens()).orElse(performance.getMaidens()));
        performance.setDotBalls(Optional.ofNullable(updatedData.getDotBalls()).orElse(performance.getDotBalls()));
        performance.setHattricks(Optional.ofNullable(updatedData.getHattricks()).orElse(performance.getHattricks()));
        performance.setCatches(Optional.ofNullable(updatedData.getCatches()).orElse(performance.getCatches()));
        performance.setRunOuts(Optional.ofNullable(updatedData.getRunOuts()).orElse(performance.getRunOuts()));
        performance.setStumpings(Optional.ofNullable(updatedData.getStumpings()).orElse(performance.getStumpings()));

        PlayerPerformance savedPerformance = playerPerformanceRepository.save(performance); // ✅ FIXED: Proper save method
        return ResponseEntity.ok(savedPerformance);
    }

    @DeleteMapping("/{playerId}/{matchId}")
    public ResponseEntity<Void> deletePlayerPerformance(
            @PathVariable Integer playerId, @PathVariable Integer matchId) {
        PlayerPerformanceId id = new PlayerPerformanceId(playerId, matchId);
        playerPerformanceService.deletePlayerPerformance(id);
        return ResponseEntity.noContent().build();
    }
}
