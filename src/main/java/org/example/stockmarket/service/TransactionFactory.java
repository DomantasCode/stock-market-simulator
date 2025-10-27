package org.example.stockmarket.service;

import org.example.stockmarket.model.Transaction;
import org.example.stockmarket.util.Constants;
import org.example.stockmarket.util.TransactionType;

/**
 * Factory Method Pattern - sandorių kūrimas
 * Centralizuoja sandorių kūrimo logiką ir mokesčių skaičiavimą
 */
public class TransactionFactory {

    /**
     * Sukuria pirkimo sandorį
     */
    public static Transaction createBuyTransaction(int quantity, double price) {
        double fee = calculateFee(price, quantity);
        return new Transaction(TransactionType.BUY, quantity, price, fee);
    }

    /**
     * Sukuria pardavimo sandorį
     */
    public static Transaction createSellTransaction(int quantity, double price) {
        double fee = calculateFee(price, quantity);
        return new Transaction(TransactionType.SELL, quantity, price, fee);
    }

    /**
     * Sukuria HOLD sandorį (nieko nedaro)
     */
    public static Transaction createHoldTransaction() {
        return new Transaction(TransactionType.HOLD, 0, 0.0, 0.0);
    }

    /**
     * Skaičiuoja mokestį už sandorį
     */
    private static double calculateFee(double price, int quantity) {
        return price * quantity * Constants.TRANSACTION_FEE_RATE;
    }
}
