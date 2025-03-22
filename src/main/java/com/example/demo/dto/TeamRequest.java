package com.example.demo.dto;

import java.util.List;

public class TeamRequest {
    private String teamName;
    private List<Integer> playerIds; // ✅ Ensure this matches frontend payload

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public List<Integer> getPlayerIds() {
        return playerIds;
    }

    public void setPlayerIds(List<Integer> playerIds) {
        this.playerIds = playerIds;
    }
}
