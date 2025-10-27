package org.example.stockmarket.service.strategy;

import org.example.stockmarket.util.Constants;

import java.util.Random;

/**
 * Atsitiktinis kainų kitimas - ±5% volatility
 * Strategy Pattern implementacija
 */
public class RandomPriceStrategy implements PriceStrategy {
    private final Random random;

    public RandomPriceStrategy() {
        this.random = new Random();
    }

    // Constructor su seed testams
    public RandomPriceStrategy(long seed) {
        this.random = new Random(seed);
    }

    @Override
    public double generateNextPrice(double currentPrice) {
        // Generuoja pokytį nuo -5% iki +5%
        double change = (random.nextDouble() * 2 - 1) * Constants.PRICE_VOLATILITY;
        double newPrice = currentPrice * (1 + change);

        // Validacija - kaina neišeina už ribų
        return Math.max(Constants.MIN_PRICE,
                Math.min(Constants.MAX_PRICE, newPrice));
    }

    @Override
    public String getStrategyName() {
        return "Atsitiktinis";
    }
}
