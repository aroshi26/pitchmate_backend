package com.example.demo.Entities.Enum;


public enum AchievementTitle {
    BEST_ALL_ROUNDER("Best All Rounder"),
    BEST_BATSMAN("Best Batsman"),
    BEST_FIELDER("Best Fielder"),
    MOST_VALUABLE("Most Valuable");

    private final String displayName;

    AchievementTitle(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }

    public static AchievementTitle fromDisplayName(String displayName) {
        for (AchievementTitle title : AchievementTitle.values()) {
            if (title.displayName.equalsIgnoreCase(displayName)) {
                return title;
            }
        }
        throw new IllegalArgumentException("No AchievementTitle with display name " + displayName);
    }
}

