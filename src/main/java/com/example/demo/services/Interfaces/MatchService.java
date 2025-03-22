package com.example.demo.services.Interfaces;

import com.example.demo.Entities.Match;
import java.util.List;
import java.util.Optional;

public interface MatchService {
    List<Match> getAllMatches();
    Optional<Match> getMatchById(Integer matchId);
    Match createMatch(Match match);
    Match updateMatch(Integer matchId, Match match);
    void deleteMatch(Integer matchId);
}
