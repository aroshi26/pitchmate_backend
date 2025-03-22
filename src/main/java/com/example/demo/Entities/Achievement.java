package com.example.demo.Entities;

import com.example.demo.Entities.Enum.AchievementTitle;
import jakarta.persistence.*;

@Entity
@Table(name = "Achievements")
public class Achievement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer achievementId;

    @ManyToOne
    @JoinColumn(name = "matchId", nullable = false)
    private Match match;

    @ManyToOne
    @JoinColumn(name = "playerId", nullable = false)
    private Player player;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AchievementTitle title;

    // No-argument constructor
    public Achievement() {
    }

    // Parameterized constructor for convenience
    public Achievement(Match match, Player player, AchievementTitle title) {
        this.match = match;
        this.player = player;
        this.title = title;
    }

    // Getters and setters
    public Integer getAchievementId() {
        return achievementId;
    }

    public void setAchievementId(Integer achievementId) {
        this.achievementId = achievementId;
    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public AchievementTitle getTitle() {
        return title;
    }

    public void setTitle(AchievementTitle title) {
        this.title = title;
    }
}
