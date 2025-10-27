package org.example.stockmarket.service;

import org.example.stockmarket.model.Asset;
import org.example.stockmarket.model.Portfolio;
import org.example.stockmarket.util.Constants;

/**
 * Validuoja sandorius - ar galima pirkti/parduoti
 * Single Responsibility Principle - tik validacija
 */
public class TransactionValidator {

    /**
     * Tikrina ar galima pirkti akcijas
     */
    public static ValidationResult validateBuy(Portfolio portfolio, Asset asset, int quantity) {
        if (quantity <= 0) {
            return ValidationResult.error("Kiekis turi būti teigiamas!");
        }

        double price = asset.getCurrentPrice();
        double fee = price * quantity * Constants.TRANSACTION_FEE_RATE;
        double totalCost = (price * quantity) + fee;

        if (portfolio.getBalance() < totalCost) {
            return ValidationResult.error(
                    String.format("Nepakanka lėšų! Reikia: %.2f EUR, Turite: %.2f EUR",
                            totalCost, portfolio.getBalance())
            );
        }

        return ValidationResult.success();
    }

    /**
     * Tikrina ar galima parduoti akcijas
     */
    public static ValidationResult validateSell(Portfolio portfolio, Asset asset, int quantity) {
        if (quantity <= 0) {
            return ValidationResult.error("Kiekis turi būti teigiamas!");
        }

        int currentShares = portfolio.getShares(asset.getName());
        if (currentShares < quantity) {
            return ValidationResult.error(
                    String.format("Nepakanka akcijų! Turite: %d vnt, bandote parduoti: %d vnt",
                            currentShares, quantity)
            );
        }

        return ValidationResult.success();
    }

    /**
     * Rezultato klasė validacijai
     */
    public static class ValidationResult {
        private final boolean valid;
        private final String errorMessage;

        private ValidationResult(boolean valid, String errorMessage) {
            this.valid = valid;
            this.errorMessage = errorMessage;
        }

        public static ValidationResult success() {
            return new ValidationResult(true, null);
        }

        public static ValidationResult error(String message) {
            return new ValidationResult(false, message);
        }

        public boolean isValid() {
            return valid;
        }

        public String getErrorMessage() {
            return errorMessage;
        }
    }
}
