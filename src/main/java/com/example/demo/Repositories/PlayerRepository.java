package com.example.demo.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.example.demo.Entities.Player;
import java.util.List;

public interface PlayerRepository extends JpaRepository<Player, Integer> {

    // ✅ Fetch players for a given team via PlayerTeam
    @Query("SELECT p FROM Player p JOIN PlayerTeam pt ON p.playerId = pt.player.playerId WHERE pt.team.teamId = :teamId")
    List<Player> findPlayersByTeamId(@Param("teamId") Integer teamId);
}
