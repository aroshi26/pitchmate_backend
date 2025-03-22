package com.example.demo.Controllers;

import com.example.demo.Entities.Player;
import com.example.demo.Entities.PlayerRole;
import com.example.demo.Repositories.PlayerRoleRepository;
import com.example.demo.services.Interfaces.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/players")
@CrossOrigin(origins = "http://localhost:5173") // ✅ Ensure frontend access

public class PlayerController {

    @Autowired
    private PlayerService playerService;
    @Autowired
    private PlayerRoleRepository playerRoleRepository; // ✅ Add PlayerRoleRepository
    @PreAuthorize("hasRole('ROLE_Admin') or hasRole('ROLE_Coach') or hasRole('ROLE_MedicalOfficer')")
    @GetMapping
    public ResponseEntity<List<Player>> getAllPlayers() {
        List<Player> players = playerService.getAllPlayers();

        if (players == null || players.isEmpty()) {
            return ResponseEntity.ok(Collections.emptyList()); // ✅ Return an empty array instead of null
        }

        return ResponseEntity.ok(players);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Player> getPlayerById(@PathVariable Integer id) {
        return playerService.getPlayerById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Player> createPlayer(@RequestBody Player newPlayer) {
        Player player = new Player();

        // ✅ Only set non-null fields
        if (newPlayer.getPlayerName() != null) {
            player.setPlayerName(newPlayer.getPlayerName());
        }
        if (newPlayer.getContact() != null) {
            player.setContact(newPlayer.getContact());
        }
        if (newPlayer.getBattingStyle() != null) {
            player.setBattingStyle(newPlayer.getBattingStyle());
        }
        if (newPlayer.getBowlingStyle() != null) {
            player.setBowlingStyle(newPlayer.getBowlingStyle());
        }
        if (newPlayer.getBorn() != null) {
            player.setBorn(newPlayer.getBorn());
        }
        if (newPlayer.getYearJoined() != null) {
            player.setYearJoined(newPlayer.getYearJoined());
        }
        if (newPlayer.getPlayerRole() != null) {
            player.setPlayerRole(newPlayer.getPlayerRole());
        }

        Player savedPlayer = playerService.createPlayer(player);
        return ResponseEntity.ok(savedPlayer);
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updatePlayer(@PathVariable Integer id, @RequestBody Map<String, Object> request) {
        Optional<Player> playerOpt = playerService.getPlayerById(id);

        if (playerOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Player player = playerOpt.get();

        // ✅ Update fields only if they are present in the request
        if (request.containsKey("playerName")) {
            player.setPlayerName((String) request.get("playerName"));
        }
        if (request.containsKey("contact")) {
            player.setContact((String) request.get("contact"));
        }
        if (request.containsKey("battingStyle")) {
            player.setBattingStyle((String) request.get("battingStyle"));
        }
        if (request.containsKey("bowlingStyle")) {
            player.setBowlingStyle((String) request.get("bowlingStyle"));
        }

        // ✅ Handle Player Role Update
        if (request.containsKey("playerRoleId")) {
            Integer playerRoleId = (Integer) request.get("playerRoleId");
            Optional<PlayerRole> roleOpt = playerRoleRepository.findById(playerRoleId);

            if (roleOpt.isEmpty()) {
                return ResponseEntity.badRequest().body("Error: Invalid Player Role ID!");
            }

            player.setPlayerRole(roleOpt.get()); // ✅ Set the new role
        }

        Player updatedPlayer = playerService.savePlayer(player);
        return ResponseEntity.ok(updatedPlayer);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlayer(@PathVariable Integer id) {
        playerService.deletePlayer(id);
        return ResponseEntity.noContent().build();
    }
}
