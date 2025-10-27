package org.example.stockmarket.util;

/**
 * Konstantos visam projektui (DRY principas)
 */
public class Constants {
    // Žaidimo parametrai
    public static final int INITIAL_BALANCE = 1000;
    public static final int MAX_TURNS = 10;
    public static final int MAX_TRANSACTIONS = 10; // Maksimalus sandorių skaičius
    public static final double BANKRUPTCY_THRESHOLD = 50.0;

    // Kainos parametrai
    public static final double INITIAL_STOCK_PRICE = 50.0;
    public static final double MIN_PRICE = 10.0;
    public static final double MAX_PRICE = 200.0;
    public static final double PRICE_VOLATILITY = 0.05; // ±5%

    // Sandorių parametrai
    public static final double TRANSACTION_FEE_RATE = 0.001; // 0.1%

    // ASCII grafikas
    public static final int CHART_HEIGHT = 10;
    public static final int CHART_WIDTH = 15;

    // Akcijos pavadinimas
    public static final String STOCK_NAME = "TECH";

    // Uždrausti instantiation
    private Constants() {
        throw new UnsupportedOperationException("Utility class");
    }
}
