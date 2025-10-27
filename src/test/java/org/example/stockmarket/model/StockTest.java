package org.example.stockmarket.model;

import org.example.stockmarket.util.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Stock klasės testai
 */
class StockTest {
    private Stock stock;

    @BeforeEach
    void setUp() {
        stock = new Stock("TECH", Constants.INITIAL_STOCK_PRICE);
    }

    @Test
    void testStockInitialization() {
        assertEquals("TECH", stock.getName());
        assertEquals(Constants.INITIAL_STOCK_PRICE, stock.getCurrentPrice());
        assertEquals(1, stock.getPriceHistory().size());
    }

    @Test
    void testPriceUpdate() {
        double newPrice = 55.0;
        stock.updatePrice(newPrice);

        assertEquals(newPrice, stock.getCurrentPrice());
        assertEquals(2, stock.getPriceHistory().size());
        assertEquals(5.0, stock.getPriceChange(), 0.01);
    }

    @Test
    void testPriceValidation_MinBound() {
        // Testas ar kaina neišeina už minimalios ribos
        stock.updatePrice(5.0); // Žemiau MIN_PRICE

        assertTrue(stock.getCurrentPrice() >= Constants.MIN_PRICE);
    }

    @Test
    void testPriceValidation_MaxBound() {
        // Testas ar kaina neišeina už maksimalios ribos
        stock.updatePrice(250.0); // Virš MAX_PRICE

        assertTrue(stock.getCurrentPrice() <= Constants.MAX_PRICE);
    }

    @Test
    void testPriceChangeCalculation() {
        double oldPrice = stock.getCurrentPrice();
        double newPrice = 60.0;

        stock.updatePrice(newPrice);

        double expectedChange = newPrice - oldPrice;
        double expectedPercent = (expectedChange / oldPrice) * 100;

        assertEquals(expectedChange, stock.getPriceChange(), 0.01);
        assertEquals(expectedPercent, stock.getPriceChangePercent(), 0.01);
    }
}
