package org.example.stockmarket.model;

import org.example.stockmarket.util.TransactionType;

import java.time.LocalDateTime;

/**
 * Sandoris - įrašo informaciją apie pirkimą/pardavimą
 * Encapsulation - visi laukai private su getteriais
 */
public class Transaction {
    private final TransactionType type;
    private final int quantity;
    private final double price;
    private final double totalCost;
    private final double fee;
    private final LocalDateTime timestamp;

    public Transaction(TransactionType type, int quantity, double price, double fee) {
        this.type = type;
        this.quantity = quantity;
        this.price = price;
        this.fee = fee;
        this.totalCost = (price * quantity) + fee;
        this.timestamp = LocalDateTime.now();
    }

    public TransactionType getType() {
        return type;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public double getFee() {
        return fee;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public boolean isSuccessful() {
        return type != null;
    }

    @Override
    public String toString() {
        return String.format("%s: %d vnt @ %.2f EUR (mokestis: %.2f EUR)",
                type.getDisplayName(), quantity, price, fee);
    }
}
