package com.example.demo.Repositories;

import com.example.demo.Entities.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MatchRepository extends JpaRepository<Match, Integer> {

    @Query("SELECT DISTINCT m FROM Match m " +
            "JOIN m.assignedTeam t " +
            "JOIN t.teamPlayers tp " +  // ✅ Fix: Use 'teamPlayers' (matches Team entity)
            "JOIN tp.player p " +
            "WHERE p.playerId = :playerId")
    List<Match> findMatchesByPlayer(@Param("playerId") Integer playerId);


    @Query("SELECT m FROM Match m LEFT JOIN FETCH m.assignedTeam WHERE m.matchId = :matchId")
    Optional<Match> findMatchDetails(@Param("matchId") Integer matchId);

}
