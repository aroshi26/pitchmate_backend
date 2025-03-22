package com.example.demo.Repositories;

import com.example.demo.Entities.Player;
import com.example.demo.Entities.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamRepository extends JpaRepository<Team, Integer> {
    @Query("SELECT DISTINCT p FROM Player p " +
            "JOIN p.teamPlayers tp " +  // ✅ Correct reference to the relationship
            "JOIN tp.team t " +
            "WHERE t.teamId = :teamId")
    List<Player> findPlayersByTeamId(@Param("teamId") Integer teamId);
}
