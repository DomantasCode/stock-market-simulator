package org.example.stockmarket.model;

import org.example.stockmarket.util.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Portfelis - valdo balansą, akcijas ir sandorius
 * Encapsulation - visi laukai private
 * Polymorphism - gali laikyti skirtingus Asset tipus
 */
public class Portfolio {
    private double balance;
    private final Map<String, Integer> holdings; // Asset name -> quantity
    private final List<Transaction> transactions;
    private final double initialBalance;

    public Portfolio(double initialBalance) {
        this.balance = initialBalance;
        this.initialBalance = initialBalance;
        this.holdings = new HashMap<>();
        this.transactions = new ArrayList<>();
    }

    public boolean canBuy(double price, int quantity) {
        double cost = calculateTotalCost(price, quantity);
        return balance >= cost && quantity > 0;
    }

    public void buy(Asset asset, int quantity, double fee) {
        double totalCost = (asset.getCurrentPrice() * quantity) + fee;
        balance -= totalCost;

        String assetName = asset.getName();
        holdings.put(assetName, holdings.getOrDefault(assetName, 0) + quantity);
    }

    public boolean canSell(String assetName, int quantity) {
        return holdings.getOrDefault(assetName, 0) >= quantity && quantity > 0;
    }

    public void sell(Asset asset, int quantity, double fee) {
        String assetName = asset.getName();
        double revenue = (asset.getCurrentPrice() * quantity) - fee;
        balance += revenue;

        int currentHolding = holdings.get(assetName);
        if (currentHolding == quantity) {
            holdings.remove(assetName);
        } else {
            holdings.put(assetName, currentHolding - quantity);
        }
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    public double getTotalValue(Asset asset) {
        int shares = holdings.getOrDefault(asset.getName(), 0);
        double stockValue = shares * asset.getCurrentPrice();
        return balance + stockValue;
    }

    public boolean isBankrupt(Asset asset) {
        return getTotalValue(asset) < Constants.BANKRUPTCY_THRESHOLD;
    }

    private double calculateTotalCost(double price, int quantity) {
        double subtotal = price * quantity;
        double fee = subtotal * Constants.TRANSACTION_FEE_RATE;
        return subtotal + fee;
    }

    // Getters
    public double getBalance() {
        return balance;
    }

    public int getShares(String assetName) {
        return holdings.getOrDefault(assetName, 0);
    }

    public double getStockValue(Asset asset) {
        return getShares(asset.getName()) * asset.getCurrentPrice();
    }

    public List<Transaction> getTransactions() {
        return new ArrayList<>(transactions); // Defensive copy
    }

    public int getSuccessfulTransactionCount() {
        return (int) transactions.stream()
                .filter(Transaction::isSuccessful)
                .count();
    }

    /**
     * Grąžina tik BUY/SELL sandorių skaičių (be HOLD)
     */
    public int getBuySellTransactionCount() {
        return (int) transactions.stream()
                .filter(t -> t.getQuantity() > 0)
                .count();
    }

    public double getProfit() {
        return balance - initialBalance;
    }

    public double getProfitPercent() {
        return (getProfit() / initialBalance) * 100;
    }
}
