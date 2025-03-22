package com.example.demo.Controllers;

import com.example.demo.Entities.Player;
import com.example.demo.Entities.Team;
import com.example.demo.Entities.TeamPlayer;
import com.example.demo.Repositories.PlayerRepository;
import com.example.demo.Repositories.TeamPlayerRepository;
import com.example.demo.Repositories.TeamRepository;
import com.example.demo.dto.TeamRequest;
import com.example.demo.services.Interfaces.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/teams")
@CrossOrigin(origins = "http://localhost:5173")
public class TeamController {

    @Autowired
    private TeamService teamService;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private TeamPlayerRepository teamPlayerRepository;

    // ✅ Get all teams
    @GetMapping
    public ResponseEntity<List<Team>> getAllTeams() {
        return ResponseEntity.ok(teamRepository.findAll());
    }

    // ✅ Get team by ID
    @GetMapping("/{teamId}")
    public ResponseEntity<Team> getTeamById(@PathVariable Integer teamId) {
        Optional<Team> teamOpt = teamRepository.findById(teamId);
        return teamOpt.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // ✅ Create a new team and assign players
    @PostMapping
    public ResponseEntity<?> createTeam(@RequestBody TeamRequest teamRequest) {
        if (teamRequest.getTeamName() == null || teamRequest.getPlayerIds() == null || teamRequest.getPlayerIds().isEmpty()) {
            return ResponseEntity.badRequest().body("Team name and at least one player are required.");
        }

        try {
            // ✅ Create and save the team
            Team team = new Team();
            team.setTeamName(teamRequest.getTeamName());
            team = teamRepository.save(team);

            // ✅ Assign players to the team
            List<TeamPlayer> teamPlayers = new ArrayList<>();
            for (Integer playerId : teamRequest.getPlayerIds()) {
                Optional<Player> playerOpt = playerRepository.findById(playerId);
                Team finalTeam = team;
                playerOpt.ifPresent(player -> {
                    TeamPlayer teamPlayer = new TeamPlayer(finalTeam, player);
                    teamPlayers.add(teamPlayer);
                });
            }

            // ✅ Save all player assignments
            teamPlayerRepository.saveAll(teamPlayers);
            return ResponseEntity.ok("Team created and players assigned successfully!");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating team: " + e.getMessage());
        }
    }

    // ✅ Update team details
    @PutMapping("/{teamId}")
    public ResponseEntity<?> updateTeam(@PathVariable Integer teamId, @RequestBody TeamRequest teamRequest) {
        Optional<Team> teamOpt = teamRepository.findById(teamId);
        if (teamOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Team team = teamOpt.get();
        team.setTeamName(teamRequest.getTeamName());

        // ✅ Remove existing player assignments
        teamPlayerRepository.deleteByTeam(team);

        // ✅ Reassign players
        List<TeamPlayer> teamPlayers = new ArrayList<>();
        for (Integer playerId : teamRequest.getPlayerIds()) {
            Optional<Player> playerOpt = playerRepository.findById(playerId);
            playerOpt.ifPresent(player -> {
                TeamPlayer teamPlayer = new TeamPlayer(team, player);
                teamPlayers.add(teamPlayer);
            });
        }

        // ✅ Save new player assignments
        teamPlayerRepository.saveAll(teamPlayers);
        teamRepository.save(team);

        return ResponseEntity.ok("Team updated successfully!");
    }

    // ✅ Delete a team and its player assignments
    @DeleteMapping("/{teamId}")
    public ResponseEntity<?> deleteTeam(@PathVariable Integer teamId) {
        Optional<Team> teamOpt = teamRepository.findById(teamId);
        if (teamOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        teamPlayerRepository.deleteByTeam(teamOpt.get()); // ✅ Delete player-team relations
        teamRepository.delete(teamOpt.get()); // ✅ Delete team itself

        return ResponseEntity.ok("Team deleted successfully!");
    }

    // ✅ Get all players assigned to a team
    @GetMapping("/{teamId}/players")
    public ResponseEntity<List<Player>> getPlayersByTeam(@PathVariable Integer teamId) {
        Optional<Team> teamOpt = teamRepository.findById(teamId);
        if (teamOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
        }

        // ✅ Fetch players from `team_players` mapping table
        List<Player> players = teamPlayerRepository.findByTeam(teamOpt.get()).stream()
                .map(TeamPlayer::getPlayer)
                .collect(Collectors.toList());

        return ResponseEntity.ok(players);
    }
}
