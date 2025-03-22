package com.example.demo.dto;

public class PlayerDTO {

    private String playerName;
    private String roleName;

    // Constructor matching the query's parameters
    public PlayerDTO(String playerName, String roleName) {
        this.playerName = playerName;
        this.roleName = roleName;
    }

    // Getters and Setters
    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
