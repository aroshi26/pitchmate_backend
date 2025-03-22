package com.example.demo.services;

import com.example.demo.Entities.Player;
import com.example.demo.Entities.PlayerRole;
import com.example.demo.Repositories.PlayerRepository;
import com.example.demo.Repositories.PlayerRoleRepository;
import com.example.demo.services.Interfaces.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PlayerServiceImpl implements PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private PlayerRoleRepository playerRoleRepository; // ✅ Add PlayerRoleRepository

    @Override
    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    @Override
    public Optional<Player> getPlayerById(Integer id) {
        return playerRepository.findById(id);
    }

    @Override
    public Player createPlayer(Player player) {
        // ✅ Fetch and Assign Existing Player Role Before Saving
        Integer roleId = player.getPlayerRole().getPlayerRoleId();
        PlayerRole existingRole = playerRoleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Player Role not found: " + roleId));

        player.setPlayerRole(existingRole); // ✅ Assign existing role

        return playerRepository.save(player);
    }
    @Override
    public Player savePlayer(Player player) {
        return playerRepository.save(player);
    }
    @Override
    public Player updatePlayer(Integer id, Player player) {
        return playerRepository.findById(id).map(existing -> {
            existing.setPlayerName(player.getPlayerName());
            existing.setContact(player.getContact());

            // ✅ Ensure Player Role Exists Before Updating
            Integer roleId = player.getPlayerRole().getPlayerRoleId();
            PlayerRole existingRole = playerRoleRepository.findById(roleId)
                    .orElseThrow(() -> new RuntimeException("Player Role not found: " + roleId));

            existing.setPlayerRole(existingRole);

            return playerRepository.save(existing);
        }).orElseThrow(() -> new RuntimeException("Player not found"));
    }

    @Override
    public void deletePlayer(Integer id) {
        playerRepository.deleteById(id);
    }}