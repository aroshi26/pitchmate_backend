package com.example.demo.Entities;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class PlayerPerformanceId implements Serializable {

    private Integer playerId;
    private Integer matchId;

    // No-argument constructor
    public PlayerPerformanceId() {
    }

    // Parameterized constructor
    public PlayerPerformanceId(Integer playerId, Integer matchId) {
        this.playerId = playerId;
        this.matchId = matchId;
    }

    public Integer getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Integer playerId) {
        this.playerId = playerId;
    }

    public Integer getMatchId() {
        return matchId;
    }

    public void setMatchId(Integer matchId) {
        this.matchId = matchId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerPerformanceId that = (PlayerPerformanceId) o;
        return Objects.equals(playerId, that.playerId) &&
                Objects.equals(matchId, that.matchId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerId, matchId);
    }
}
