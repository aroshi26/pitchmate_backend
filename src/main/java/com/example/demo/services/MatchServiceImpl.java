package com.example.demo.services;

import com.example.demo.Entities.Match;
import com.example.demo.Repositories.MatchRepository;
import com.example.demo.services.Interfaces.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class MatchServiceImpl implements MatchService {

    private final MatchRepository matchRepository;

    @Autowired
    public MatchServiceImpl(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    @Override
    public List<Match> getAllMatches() {
        return matchRepository.findAll();
    }

    @Override
    public Optional<Match> getMatchById(Integer matchId) {
        return matchRepository.findById(matchId);
    }

    @Override
    public Match createMatch(Match match) {
        return matchRepository.save(match);
    }

    @Override
    public Match updateMatch(Integer matchId, Match match) {
        Optional<Match> existingMatchOpt = matchRepository.findById(matchId);
        if (existingMatchOpt.isPresent()) {
            Match existingMatch = existingMatchOpt.get();
            existingMatch.setMatchType(match.getMatchType());
            existingMatch.setOpponentTeam(match.getOpponentTeam());
            existingMatch.setOpponentTeamStrength(match.getOpponentTeamStrength());
            existingMatch.setLeagueOrNot(match.getLeagueOrNot());
            existingMatch.setMatchLocation(match.getMatchLocation());
            return matchRepository.save(existingMatch);
        } else {
            // Depending on your business rules, you might throw a custom exception here
            throw new RuntimeException("Match with ID " + matchId + " not found");
        }
    }

    @Override
    public void deleteMatch(Integer matchId) {
        matchRepository.deleteById(matchId);
    }
}
