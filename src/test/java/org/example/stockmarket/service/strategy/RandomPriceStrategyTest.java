package org.example.stockmarket.service.strategy;

import org.example.stockmarket.util.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * RandomPriceStrategy testai
 */
class RandomPriceStrategyTest {
    private RandomPriceStrategy strategy;

    @BeforeEach
    void setUp() {
        // Naudojame seed deterministic rezultatams
        strategy = new RandomPriceStrategy(12345L);
    }

    @Test
    void testPriceGeneration_WithinBounds() {
        // Testas ar generuota kaina neišeina už ribų
        double currentPrice = 50.0;

        for (int i = 0; i < 100; i++) {
            double newPrice = strategy.generateNextPrice(currentPrice);

            assertTrue(newPrice >= Constants.MIN_PRICE,
                    "Kaina neturi būti mažesnė už MIN_PRICE: " + newPrice);
            assertTrue(newPrice <= Constants.MAX_PRICE,
                    "Kaina neturi būti didesnė už MAX_PRICE: " + newPrice);

            currentPrice = newPrice;
        }
    }

    @Test
    void testPriceGeneration_VolatilityRange() {
        // Testas ar kainų pokytis yra ±5% ribose (dažniausiai)
        double currentPrice = 100.0;
        int withinRange = 0;
        int total = 1000;

        for (int i = 0; i < total; i++) {
            RandomPriceStrategy tempStrategy = new RandomPriceStrategy();
            double newPrice = tempStrategy.generateNextPrice(currentPrice);
            double change = Math.abs((newPrice - currentPrice) / currentPrice);

            if (change <= Constants.PRICE_VOLATILITY) {
                withinRange++;
            }
        }

        // Dauguma kainų turėtų būti ±5% ribose
        double percentage = (double) withinRange / total;
        assertTrue(percentage > 0.8, "Per daug kainų išeina už volatility ribų");
    }

    @Test
    void testStrategyName() {
        assertEquals("Atsitiktinis", strategy.getStrategyName());
    }

    @Test
    void testPriceGeneration_MinBoundary() {
        // Testas ar teisingai validuoja minimalią kainą
        double veryLowPrice = 5.0; // Žemiau MIN_PRICE

        double newPrice = strategy.generateNextPrice(veryLowPrice);

        assertTrue(newPrice >= Constants.MIN_PRICE);
    }
}
