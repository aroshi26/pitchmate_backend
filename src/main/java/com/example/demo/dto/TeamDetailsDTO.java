package com.example.demo.dto;

import java.util.List;

public class TeamDetailsDTO {
    private String teamName;
    private List<PlayerDTO> players;

    public TeamDetailsDTO(String teamName, List<PlayerDTO> players) {
        this.teamName = teamName;
        this.players = players;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public List<PlayerDTO> getPlayers() {
        return players;
    }

    public void setPlayers(List<PlayerDTO> players) {
        this.players = players;
    }
// Constructor, Getters, and Setters
}
