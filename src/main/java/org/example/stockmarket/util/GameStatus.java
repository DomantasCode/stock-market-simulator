package org.example.stockmarket.util;

/**
 * Žaidimo būsenos
 */
public enum GameStatus {
    PLAYING("Žaidimas tęsiasi"),
    FINISHED("Žaidimas baigtas"),
    BANKRUPT("Bankrotas");

    private final String displayName;

    GameStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
