package org.example.stockmarket.service.strategy;

import org.example.stockmarket.util.Constants;

import java.util.Random;

/**
 * Kainų kitimas su trendu - pamažu kyla arba krenta
 * Papildoma Strategy Pattern implementacija (bonus)
 */
public class TrendPriceStrategy implements PriceStrategy {
    private final Random random;
    private double trendDirection; // Trendas: teigiamas (kyla) arba neigiamas (krenta)
    private int trendDuration; // Kiek ėjimų dar tęsis trendas

    public TrendPriceStrategy() {
        this.random = new Random();
        generateNewTrend();
    }

    @Override
    public double generateNextPrice(double currentPrice) {
        // Mažiname trendo trukmę
        trendDuration--;

        // Jei trendas baigėsi, generuojame naują
        if (trendDuration <= 0) {
            generateNewTrend();
        }

        // Kainų pokytis su trendu ir atsitiktiniu komponentu
        double randomComponent = (random.nextDouble() * 2 - 1) * Constants.PRICE_VOLATILITY * 0.5;
        double trendComponent = trendDirection * Constants.PRICE_VOLATILITY * 0.5;
        double totalChange = randomComponent + trendComponent;

        double newPrice = currentPrice * (1 + totalChange);

        // Validacija
        return Math.max(Constants.MIN_PRICE,
                Math.min(Constants.MAX_PRICE, newPrice));
    }

    private void generateNewTrend() {
        // Naujas trendas trunka 3-7 ėjimus
        this.trendDuration = 3 + random.nextInt(5);
        // Trendas gali būti kilimo (+1) arba kritimo (-1)
        this.trendDirection = random.nextBoolean() ? 1.0 : -1.0;
    }

    @Override
    public String getStrategyName() {
        String direction = trendDirection > 0 ? "Kilimas" : "Kritimas";
        return String.format("Trendas (%s, dar %d ėj.)", direction, trendDuration);
    }
}
