package com.example.demo.Entities;

import com.example.demo.Entities.Enum.MatchType;
import com.example.demo.Entities.Enum.TeamStrength;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "matches")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "match_id") // ✅ Ensure column name is explicitly set
    private Integer matchId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MatchType matchType;

    @Column(nullable = false, length = 100)
    private String opponentTeam;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TeamStrength opponentTeamStrength;

    @Column(nullable = false)
    private Boolean leagueOrNot;

    @Column(nullable = false, length = 200)
    private String matchLocation;

    @Column(nullable = false)
    private LocalDate matchDate;

    private String result;

    @ManyToOne(fetch = FetchType.LAZY) // ✅ Lazy loading prevents unnecessary queries
    @JoinColumn(name = "team_id", referencedColumnName = "team_id") // ✅ Single correct @JoinColumn
    @JsonIgnoreProperties("matches") // ✅ Prevents recursion
    private Team assignedTeam;

    // ✅ Getters & Setters
    public Integer getMatchId() { return matchId; }
    public void setMatchId(Integer matchId) { this.matchId = matchId; }

    public MatchType getMatchType() { return matchType; }
    public void setMatchType(MatchType matchType) { this.matchType = matchType; }

    public String getOpponentTeam() { return opponentTeam; }
    public void setOpponentTeam(String opponentTeam) { this.opponentTeam = opponentTeam; }

    public TeamStrength getOpponentTeamStrength() { return opponentTeamStrength; }
    public void setOpponentTeamStrength(TeamStrength opponentTeamStrength) { this.opponentTeamStrength = opponentTeamStrength; }

    public Boolean getLeagueOrNot() { return leagueOrNot; }
    public void setLeagueOrNot(Boolean leagueOrNot) { this.leagueOrNot = leagueOrNot; }

    public String getMatchLocation() { return matchLocation; }
    public void setMatchLocation(String matchLocation) { this.matchLocation = matchLocation; }

    public LocalDate getMatchDate() { return matchDate; }
    public void setMatchDate(LocalDate matchDate) { this.matchDate = matchDate; }

    public String getResult() { return result; }
    public void setResult(String result) { this.result = result; }

    public Team getAssignedTeam() { return assignedTeam; }
    public void setAssignedTeam(Team assignedTeam) { this.assignedTeam = assignedTeam; }
}
