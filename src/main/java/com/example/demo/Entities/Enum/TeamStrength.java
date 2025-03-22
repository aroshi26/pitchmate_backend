package com.example.demo.Entities.Enum;

public enum TeamStrength {
    ENTRY_LEVEL("Entry Level"),
    MID_LEVEL("Mid Level"),
    HIGH_LEVEL("High Level");

    private final String displayName;

    TeamStrength(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}

