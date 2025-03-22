package com.example.demo.services;


import com.example.demo.Entities.PlayerPerformance;
import com.example.demo.Entities.PlayerPerformanceId;
import com.example.demo.Repositories.PlayerPerformanceRepository;
import com.example.demo.dto.PlayerAggregatedStats;
import com.example.demo.services.Interfaces.PlayerPerformanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class PlayerPerformanceServiceImpl implements PlayerPerformanceService {

    @Autowired
    private PlayerPerformanceRepository playerPerformanceRepository;

    @Override
    public List<PlayerPerformance> getAllPlayerPerformances() {
        return playerPerformanceRepository.findAll();
    }

    @Override
    public Optional<PlayerPerformance> getPlayerPerformanceById(PlayerPerformanceId id) {
        return playerPerformanceRepository.findById(id);
    }

    @Override
    public PlayerPerformance createPlayerPerformance(PlayerPerformance performance) {
        return playerPerformanceRepository.save(performance);
    }
    @Override
    public PlayerPerformance updatePlayerPerformance(PlayerPerformanceId id, PlayerPerformance newPerformance) {
        Optional<PlayerPerformance> existingPerformanceOpt = playerPerformanceRepository.findById(id);

        PlayerPerformance playerPerformance = existingPerformanceOpt.orElseGet(() -> {
            PlayerPerformance newRecord = new PlayerPerformance(id);
            newRecord.setId(id);
            return newRecord;
        });

        // ✅ Update only non-null fields
        if (newPerformance.getInnings() != null) playerPerformance.setInnings(newPerformance.getInnings());
        if (newPerformance.getRunsScored() != null) playerPerformance.setRunsScored(newPerformance.getRunsScored());
        if (newPerformance.getBallsFaced() != null) playerPerformance.setBallsFaced(newPerformance.getBallsFaced());
        if (newPerformance.getFours() != null) playerPerformance.setFours(newPerformance.getFours());
        if (newPerformance.getSixes() != null) playerPerformance.setSixes(newPerformance.getSixes());
        if (newPerformance.getDucks() != null) playerPerformance.setDucks(newPerformance.getDucks());
        if (newPerformance.getDismissalsInvolved() != null) playerPerformance.setDismissalsInvolved(newPerformance.getDismissalsInvolved());
        if (newPerformance.getOversBowled() != null) playerPerformance.setOversBowled(newPerformance.getOversBowled());
        if (newPerformance.getBallsBowled() != null) playerPerformance.setBallsBowled(newPerformance.getBallsBowled());
        if (newPerformance.getRunsConceded() != null) playerPerformance.setRunsConceded(newPerformance.getRunsConceded());
        if (newPerformance.getWicketsTaken() != null) playerPerformance.setWicketsTaken(newPerformance.getWicketsTaken());
        if (newPerformance.getCatches() != null) playerPerformance.setCatches(newPerformance.getCatches());
        if (newPerformance.getStumpings() != null) playerPerformance.setStumpings(newPerformance.getStumpings());

        return playerPerformanceRepository.save(playerPerformance);
    }

    @Override
    public void deletePlayerPerformance(PlayerPerformanceId id) {
        playerPerformanceRepository.deleteById(id);
    }


    @Override
    public PlayerAggregatedStats getAggregatedStatsByPlayerId(Integer playerId) {
        List<PlayerPerformance> performances = playerPerformanceRepository.findByIdPlayerId(playerId);

        if (performances == null || performances.isEmpty()) {
            return null; // Or return a PlayerAggregatedStats with default values
        }

        int totalInnings = 0;
        int totalRuns = 0;
        int totalBallsFaced = 0;
        int highestScore = 0;
        int totalHundreds = 0;
        int totalFifties = 0;
        int totalWickets = 0;
        int totalBallsBowled = 0;
        int totalRunsConceded = 0;
        int totalOversBowled = 0;
        int totalFiveWicketHauls = 0;
        int totalMaidens = 0;
        int totalCatches = 0;
        int totalRunOuts = 0;
        int totalStumpings = 0;
        int totalDismissalsInvolved = 0;
        int totalMatchesPlayed = 0;

        // Set to track distinct matches played
        Set<Integer> matchIds = new HashSet<>();

        for (PlayerPerformance p : performances) {
            totalInnings += Optional.ofNullable(p.getInnings()).orElse(0);

            int runs = Optional.ofNullable(p.getRunsScored()).orElse(0);
            totalRuns += runs;
            totalBallsFaced += Optional.ofNullable(p.getBallsFaced()).orElse(0);
            System.out.println("Aggregated Total Runs: " + totalRuns);

            // Highest Score
            highestScore = Math.max(highestScore, runs);

            if (runs >= 100) totalHundreds++;
            else if (runs >= 50) totalFifties++;

            totalWickets += Optional.ofNullable(p.getWicketsTaken()).orElse(0);
            totalBallsBowled += Optional.ofNullable(p.getBallsBowled()).orElse(0);
            totalRunsConceded += Optional.ofNullable(p.getRunsConceded()).orElse(0);
            totalOversBowled += Optional.ofNullable(p.getOversBowled()).orElse(0);

            totalFiveWicketHauls += Optional.ofNullable(p.getFiveWicketHauls()).orElse(0);
            totalMaidens += Optional.ofNullable(p.getMaidens()).orElse(0);
            totalCatches += Optional.ofNullable(p.getCatches()).orElse(0);
            totalRunOuts += Optional.ofNullable(p.getRunOuts()).orElse(0);
            totalStumpings += Optional.ofNullable(p.getStumpings()).orElse(0);
            totalDismissalsInvolved += Optional.ofNullable(p.getDismissalsInvolved()).orElse(0);

            // Add the matchId to the set to count distinct matches
            matchIds.add(p.getId().getMatchId());
        }

        totalMatchesPlayed = matchIds.size(); // Number of distinct matches

        PlayerAggregatedStats stats = new PlayerAggregatedStats();
        stats.setPlayerId(playerId);

        // Batting Average
        stats.setBattingAverage(totalInnings > 0 ? (double) totalRuns / totalInnings : 0);

        // Strike Rate
        stats.setStrikeRate(totalBallsFaced > 0 ? ((double) totalRuns / totalBallsFaced) * 100 : 0);

        stats.setHighestScore(highestScore);
        stats.setHundreds(totalHundreds);
        stats.setFifties(totalFifties);

        // Bowling Average
        stats.setBowlingAverage(totalWickets > 0 ? (double) totalRunsConceded / totalWickets : 0);

        // Economy Rate
        stats.setEconomyRate(totalOversBowled > 0 ? (double) totalRunsConceded / totalOversBowled : 0);

        // Bowling Strike Rate
        stats.setStrikeRateBowling(totalWickets > 0 ? (double) totalBallsBowled / totalWickets : 0);

        stats.setFiveWicketHauls(totalFiveWicketHauls);
        stats.setMaidens(totalMaidens);
        stats.setCatches(totalCatches);
        stats.setRunOuts(totalRunOuts);
        stats.setStumpings(totalStumpings);
        stats.setDismissalsInvolved(totalDismissalsInvolved);
        stats.setMatchesPlayed(totalMatchesPlayed);

        return stats;
    }





}
