package com.example.demo.Repositories;

import com.example.demo.Entities.Player;
import com.example.demo.Entities.Team;
import com.example.demo.Entities.TeamPlayer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

public interface TeamPlayerRepository extends JpaRepository<TeamPlayer, Integer> {
    @Query("SELECT DISTINCT p FROM Player p " +
            "JOIN p.teamPlayers tp " +  // ✅ Correct reference to the relationship
            "JOIN tp.team t " +
            "WHERE t.teamId = :teamId")
    List<Player> findPlayersByTeamId(@Param("teamId") Integer teamId);
    List<TeamPlayer> findByTeam(Team team);

    @Transactional
    void deleteByTeam(Team team);
}
