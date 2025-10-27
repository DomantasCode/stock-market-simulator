package org.example.stockmarket.service.strategy;

/**
 * Strategy Pattern - interface kainų generavimui
 * Abstraction - leidžia keisti kainų generavimo logiką be kodo keitimo
 */
public interface PriceStrategy {
    /**
     * Generuoja naują kainą pagal dabartinę
     * @param currentPrice dabartinė kaina
     * @return nauja kaina
     */
    double generateNextPrice(double currentPrice);

    /**
     * Grąžina strategijos pavadinimą
     */
    String getStrategyName();
}
