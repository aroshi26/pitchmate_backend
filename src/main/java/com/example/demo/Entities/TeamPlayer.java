package com.example.demo.Entities;

import jakarta.persistence.*;

@Entity
@Table(name = "team_players")  // ✅ Ensure matches DB schema
public class TeamPlayer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_id", nullable = false)
    private Player player;

    // ✅ Proper Constructor
    public TeamPlayer(Team team, Player player) {
        this.team = team;
        this.player = player;
    }

    public TeamPlayer() { }

    // ✅ Getters & Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Team getTeam() { return team; }
    public void setTeam(Team team) { this.team = team; }

    public Player getPlayer() { return player; }
    public void setPlayer(Player player) { this.player = player; }
}
