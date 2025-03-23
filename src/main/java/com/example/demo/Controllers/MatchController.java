package com.example.demo.Controllers;

import com.example.demo.Entities.Match;
import com.example.demo.Entities.Player;
import com.example.demo.Entities.Team;
import com.example.demo.Entities.TeamPlayer;
import com.example.demo.Repositories.MatchRepository;
import com.example.demo.Repositories.PlayerRepository;
import com.example.demo.Repositories.TeamPlayerRepository;
import com.example.demo.Repositories.TeamRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/matches")
public class MatchController {

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private TeamPlayerRepository teamPlayerRepository;

    // ✅ 1️⃣ Create a Match FIRST (Without Assigning a Team)
    @PostMapping
    public ResponseEntity<?> createMatch(@RequestBody Match match) {
        if (match.getMatchType() == null || match.getOpponentTeam() == null ||
                match.getMatchLocation() == null || match.getOpponentTeamStrength() == null) {
            return ResponseEntity.badRequest().body("❌ Match creation failed: Missing required fields.");
        }

        try {
            Match createdMatch = matchRepository.save(match);
            return ResponseEntity.ok(createdMatch);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("❌ Error saving match: " + e.getMessage());
        }
    }

    // ✅ 2️⃣ Assign Team to Match (Updated to match new structure)
    @PostMapping("/{matchId}/assign-team")
    public ResponseEntity<?> assignTeamToMatch(@PathVariable Integer matchId, @RequestBody Map<String, Integer> requestBody) {
        Integer teamId = requestBody.get("teamId");

        System.out.println("📢 Received Request: Assign Team " + teamId + " to Match " + matchId);

        Optional<Match> optionalMatch = matchRepository.findById(matchId);
        if (optionalMatch.isEmpty()) {
            return ResponseEntity.badRequest().body("❌ Match not found.");
        }

        Optional<Team> optionalTeam = teamRepository.findById(teamId);
        if (optionalTeam.isEmpty()) {
            return ResponseEntity.badRequest().body("❌ Team not found.");
        }

        Match match = optionalMatch.get();
        match.setAssignedTeam(optionalTeam.get());
        matchRepository.save(match); // ✅ Saves team assignment

        return ResponseEntity.ok(Map.of("message", "✅ Team assigned successfully!", "match", match));
    }

    // ✅ 3️⃣ Fetch All Matches
    @GetMapping
    public ResponseEntity<List<Match>> getAllMatches() {
        List<Match> matches = matchRepository.findAll();
        return ResponseEntity.ok(matches);
    }

    // ✅ 4️⃣ Fetch Matches for a Player
    @PreAuthorize("hasRole('ROLE_Coach') or hasRole('ROLE_Admin')")
    @GetMapping("/player/{playerId}")
    public ResponseEntity<List<Match>> getMatchesForPlayer(@PathVariable Integer playerId) {
        Optional<Player> playerOpt = playerRepository.findById(playerId);
        if (playerOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
        }

        List<Match> matches = matchRepository.findMatchesByPlayer(playerId);
        return ResponseEntity.ok(matches);
    }

    @GetMapping("/{matchId}")
    public ResponseEntity<?> getMatchDetails(@PathVariable Integer matchId) {
        Optional<Match> matchOpt = matchRepository.findById(matchId);

        if (matchOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "❌ Match not found."));
        }

        Match match = matchOpt.get();

        // ✅ Force Hibernate to initialize the assigned team if not null
        if (match.getAssignedTeam() != null) {
            Hibernate.initialize(match.getAssignedTeam());
        }

        // ✅ Fetch and initialize players list, if the team is assigned
        List<Player> players = new ArrayList<>();
        if (match.getAssignedTeam() != null) {
            players = teamPlayerRepository.findByTeam(match.getAssignedTeam())
                    .stream()
                    .map(TeamPlayer::getPlayer)
                    .collect(Collectors.toList());

            players.forEach(Hibernate::initialize); // Ensure players are fully loaded
        }

        // ✅ Return match details even if the team is not assigned
        Map<String, Object> response = new HashMap<>();
        response.put("match", match);
        response.put("team", match.getAssignedTeam() != null ? match.getAssignedTeam() : "No team assigned yet");
        response.put("players", players.isEmpty() ? "No players assigned to this match" : players);

        // ✅ Return match, venue, opponent, and other details
        response.put("matchDetails", Map.of(
                "matchType", match.getMatchType(),
                "opponentTeam", match.getOpponentTeam(),
                "matchLocation", match.getMatchLocation(),
                "matchDate", match.getMatchDate(),
                "opponentTeamStrength", match.getOpponentTeamStrength()
        ));

        return ResponseEntity.ok(response);
    }



    // ✅ 6️⃣ Update Match Result
    @PutMapping("/{matchId}/result")
    public ResponseEntity<?> updateMatchResult(@PathVariable Integer matchId, @RequestBody Map<String, String> requestBody) {
        try {
            Optional<Match> optionalMatch = matchRepository.findById(matchId);
            if (optionalMatch.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "❌ Match not found."));
            }

            Match match = optionalMatch.get();
            String result = requestBody.get("result");

            if (result == null || (!result.equalsIgnoreCase("WIN") &&
                    !result.equalsIgnoreCase("LOSS") &&
                    !result.equalsIgnoreCase("DRAW"))) {
                return ResponseEntity.badRequest().body(Map.of("error", "❌ Invalid result. Use WIN, LOSS, or DRAW."));
            }

            match.setResult(result.toUpperCase());
            matchRepository.save(match);

            return ResponseEntity.ok(Map.of("message", "✅ Match result updated successfully", "result", match.getResult()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "❌ An error occurred while updating the match result."));
        }
    }
}