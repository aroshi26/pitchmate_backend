package com.example.demo.services.Interfaces;

import com.example.demo.Entities.Player;
import java.util.List;
import java.util.Optional;

public interface PlayerService {
    List<Player> getAllPlayers();
    Optional<Player> getPlayerById(Integer id);
    Player createPlayer(Player player);

    Player savePlayer(Player player);

    Player updatePlayer(Integer id, Player player);
    void deletePlayer(Integer id);
}

