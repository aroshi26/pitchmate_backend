package com.example.demo.dto;

import com.example.demo.Entities.Match;
import com.example.demo.Entities.Player;
import com.example.demo.Entities.Team;
import java.time.LocalDate;
import java.util.List;

public class MatchDetailsDTO {
    private Integer matchId;
    private String matchType;
    private String opponentTeam;
    private String matchLocation;
    private LocalDate matchDate;
    private String result;
    private Team assignedTeam;
    private List<Player> players;

    public MatchDetailsDTO(Match match) {
        this.matchId = match.getMatchId();
        this.matchType = match.getMatchType().toString();
        this.opponentTeam = match.getOpponentTeam();
        this.matchLocation = match.getMatchLocation();
        this.matchDate = match.getMatchDate();
        this.result = match.getResult();
    }

    public void setAssignedTeam(Team assignedTeam) { this.assignedTeam = assignedTeam; }
    public void setPlayers(List<Player> players) { this.players = players; }

    public Integer getMatchId() { return matchId; }
    public String getMatchType() { return matchType; }
    public String getOpponentTeam() { return opponentTeam; }
    public String getMatchLocation() { return matchLocation; }
    public LocalDate getMatchDate() { return matchDate; }
    public String getResult() { return result; }
    public Team getAssignedTeam() { return assignedTeam; }
    public List<Player> getPlayers() { return players; }
}
