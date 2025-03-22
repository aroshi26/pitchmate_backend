package com.example.demo.Entities.Enum;
// For Matches
public enum MatchType {
    
    T20("T20"),
    ODI("ODI"),
    TEST("Test");

    private final String displayName;

    MatchType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }

    public static MatchType fromDisplayName(String displayName) {
        for (MatchType type : MatchType.values()) {
            if (type.displayName.equalsIgnoreCase(displayName)) {
                return type;
            }
        }
        throw new IllegalArgumentException("No MatchType with display name " + displayName);
    }
}
