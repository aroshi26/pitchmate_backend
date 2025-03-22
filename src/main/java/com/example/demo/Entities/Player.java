package com.example.demo.Entities;

import com.example.demo.Entities.Enum.PlayerRoleType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "players")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer playerId;

    @Column(nullable = false, length = 100)
    private String playerName;

    @Column(length = 15)
    private String contact;

    @ManyToOne
    @JoinColumn(name = "player_role_id", nullable = false)
    private PlayerRole playerRole;

    private String battingStyle;
    private String bowlingStyle;
    private String born;
    private Integer yearJoined;
    @JsonIgnore
    // ✅ Relationship with TeamPlayer
    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<TeamPlayer> teamPlayers;


    public PlayerRole getPlayerRole() {
        return playerRole;
    }

    public void setPlayerRole(PlayerRole playerRole) {
        this.playerRole = playerRole;
    }

    public String getBattingStyle() {
        return battingStyle;
    }

    public void setBattingStyle(String battingStyle) {
        this.battingStyle = battingStyle;
    }

    public String getBowlingStyle() {
        return bowlingStyle;
    }

    public void setBowlingStyle(String bowlingStyle) {
        this.bowlingStyle = bowlingStyle;
    }

    public String getBorn() {
        return born;
    }

    public void setBorn(String born) {
        this.born = born;
    }

    public Integer getYearJoined() {
        return yearJoined;
    }

    public void setYearJoined(Integer yearJoined) {
        this.yearJoined = yearJoined;
    }

    public List<TeamPlayer> getTeamPlayers() {
        return teamPlayers;
    }

    public void setTeamPlayers(List<TeamPlayer> teamPlayers) {
        this.teamPlayers = teamPlayers;
    }

    // ✅ Getters & Setters
    public Integer getPlayerId() { return playerId; }
    public void setPlayerId(Integer playerId) { this.playerId = playerId; }

    public String getPlayerName() { return playerName; }
    public void setPlayerName(String playerName) { this.playerName = playerName; }

    public String getContact() { return contact; }
    public void setContact(String contact) { this.contact = contact; }


}
