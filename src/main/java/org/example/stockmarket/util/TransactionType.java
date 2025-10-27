package org.example.stockmarket.util;

/**
 * Sandorių tipai
 */
public enum TransactionType {
    BUY("Pirkimas"),
    SELL("Pardavimas"),
    HOLD("Laukimas");

    private final String displayName;

    TransactionType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
