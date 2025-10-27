package org.example.stockmarket.service;

import org.example.stockmarket.model.Portfolio;
import org.example.stockmarket.model.Stock;
import org.example.stockmarket.util.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * TransactionValidator testai
 */
class TransactionValidatorTest {
    private Portfolio portfolio;
    private Stock stock;

    @BeforeEach
    void setUp() {
        portfolio = new Portfolio(Constants.INITIAL_BALANCE);
        stock = new Stock("TECH", 50.0);
    }

    @Test
    void testValidateBuy_Success() {
        // Validus pirkimas
        TransactionValidator.ValidationResult result =
                TransactionValidator.validateBuy(portfolio, stock, 10);

        assertTrue(result.isValid());
        assertNull(result.getErrorMessage());
    }

    @Test
    void testValidateBuy_InsufficientFunds() {
        // Pirkimas be pinigų
        TransactionValidator.ValidationResult result =
                TransactionValidator.validateBuy(portfolio, stock, 1000);

        assertFalse(result.isValid());
        assertNotNull(result.getErrorMessage());
        assertTrue(result.getErrorMessage().contains("Nepakanka"));
    }

    @Test
    void testValidateBuy_NegativeQuantity() {
        // Neigiamas kiekis
        TransactionValidator.ValidationResult result =
                TransactionValidator.validateBuy(portfolio, stock, -5);

        assertFalse(result.isValid());
        assertTrue(result.getErrorMessage().contains("teigiamas"));
    }

    @Test
    void testValidateSell_Success() {
        // Validus pardavimas
        portfolio.buy(stock, 10, 0.5);

        TransactionValidator.ValidationResult result =
                TransactionValidator.validateSell(portfolio, stock, 5);

        assertTrue(result.isValid());
    }

    @Test
    void testValidateSell_InsufficientShares() {
        // Bandymas parduoti daugiau nei turime
        portfolio.buy(stock, 5, 0.25);

        TransactionValidator.ValidationResult result =
                TransactionValidator.validateSell(portfolio, stock, 10);

        assertFalse(result.isValid());
        assertTrue(result.getErrorMessage().contains("Nepakanka"));
    }

    @Test
    void testValidateSell_NoShares() {
        // Bandymas parduoti neturint akcijų
        TransactionValidator.ValidationResult result =
                TransactionValidator.validateSell(portfolio, stock, 1);

        assertFalse(result.isValid());
    }
}
