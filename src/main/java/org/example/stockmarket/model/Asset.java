package org.example.stockmarket.model;

/**
 * Abstrakti bazinė klasė aktyvams (Inheritance & Abstraction)
 * Leidžia ateityje pridėti Bond, Crypto ir kitus aktyvų tipus
 */
public abstract class Asset {
    private String name;
    private double currentPrice;

    public Asset(String name, double initialPrice) {
        this.name = name;
        this.currentPrice = initialPrice;
    }

    // Abstraktus metodas - kiekvienas aktyvas turi savo kainų atnaujinimo logiką
    public abstract void updatePrice(double newPrice);

    // Getters (Encapsulation)
    public String getName() {
        return name;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    // Protected setter - leidžia paveldėtoms klasėms keisti kainą
    protected void setCurrentPrice(double price) {
        this.currentPrice = price;
    }

    @Override
    public String toString() {
        return String.format("%s: %.2f EUR", name, currentPrice);
    }
}
