package com.example.demo.Entities.Enum;

public enum PlayerRoleType {
    BATSMAN("Batsman"),
    BOWLER("Bowler"),
    ALLROUNDER("All Rounder"),
    WICKETKEEPER("Wicketkeeper");

    private final String displayName;

    PlayerRoleType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }

    public static PlayerRoleType fromDisplayName(String displayName) {
        for (PlayerRoleType role : PlayerRoleType.values()) {
            if (role.displayName.equalsIgnoreCase(displayName)) {
                return role;
            }
        }
        throw new IllegalArgumentException("No PlayerRoleType with display name " + displayName);
    }



}
