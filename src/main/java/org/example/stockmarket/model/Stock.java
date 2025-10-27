package org.example.stockmarket.model;

import org.example.stockmarket.util.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Akcija - paveldi iš Asset (Inheritance)
 * Saugo kainų istoriją ir validuoja kainas
 */
public class Stock extends Asset {
    private final List<Double> priceHistory;
    private double priceChange;
    private double priceChangePercent;

    public Stock(String name, double initialPrice) {
        super(name, initialPrice);
        this.priceHistory = new ArrayList<>();
        this.priceHistory.add(initialPrice);
        this.priceChange = 0.0;
        this.priceChangePercent = 0.0;
    }

    @Override
    public void updatePrice(double newPrice) {
        // Validacija - kaina negali būti mažesnė už MIN ar didesnė už MAX
        double validatedPrice = Math.max(Constants.MIN_PRICE,
                Math.min(Constants.MAX_PRICE, newPrice));

        double oldPrice = getCurrentPrice();
        this.priceChange = validatedPrice - oldPrice;
        this.priceChangePercent = (priceChange / oldPrice) * 100;

        setCurrentPrice(validatedPrice);
        priceHistory.add(validatedPrice);
    }

    public List<Double> getPriceHistory() {
        return new ArrayList<>(priceHistory); // Defensive copy
    }

    public double getPriceChange() {
        return priceChange;
    }

    public double getPriceChangePercent() {
        return priceChangePercent;
    }

    public String getFormattedChange() {
        String sign = priceChange >= 0 ? "+" : "";
        return String.format("%s%.2f EUR (%s%.1f%%)",
                sign, priceChange, sign, priceChangePercent);
    }
}
