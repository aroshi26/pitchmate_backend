package com.example.demo.Entities.Enum;

public enum SessionType {
    SPORT_TRAINING("Sport Training"),
    FITNESS_TRAINING("Fitness Training");

    private final String displayName;

    SessionType(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
