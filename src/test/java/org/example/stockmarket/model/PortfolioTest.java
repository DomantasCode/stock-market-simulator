package org.example.stockmarket.model;

import org.example.stockmarket.util.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Portfolio klasės testai
 */
class PortfolioTest {
    private Portfolio portfolio;
    private Stock stock;

    @BeforeEach
    void setUp() {
        portfolio = new Portfolio(Constants.INITIAL_BALANCE);
        stock = new Stock("TEST", 50.0);
    }

    @Test
    void testBuyStock_Success() {
        // Pirkimas su pakankamu balansu
        int quantity = 10;
        double price = stock.getCurrentPrice();
        double fee = price * quantity * Constants.TRANSACTION_FEE_RATE;

        assertTrue(portfolio.canBuy(price, quantity));

        portfolio.buy(stock, quantity, fee);

        assertEquals(10, portfolio.getShares("TEST"));
        assertTrue(portfolio.getBalance() < Constants.INITIAL_BALANCE);
    }

    @Test
    void testBuyStock_InsufficientFunds() {
        // Bandymas pirkti be pinigų
        double price = stock.getCurrentPrice();
        int quantity = 1000; // Per didelis kiekis

        assertFalse(portfolio.canBuy(price, quantity));
    }

    @Test
    void testSellStock_Success() {
        // Pirmiausia perkame
        portfolio.buy(stock, 10, 0.5);

        // Dabar parduodame
        assertTrue(portfolio.canSell("TEST", 5));

        double balanceBeforeSell = portfolio.getBalance();
        portfolio.sell(stock, 5, 0.25);

        assertEquals(5, portfolio.getShares("TEST"));
        assertTrue(portfolio.getBalance() > balanceBeforeSell);
    }

    @Test
    void testSellStock_InsufficientShares() {
        // Bandymas parduoti daugiau nei turime
        portfolio.buy(stock, 5, 0.25);

        assertFalse(portfolio.canSell("TEST", 10));
    }

    @Test
    void testPortfolioTotalValue() {
        // Bendros vertės skaičiavimas
        portfolio.buy(stock, 10, 0.5);

        double expectedValue = portfolio.getBalance() + (10 * stock.getCurrentPrice());
        double actualValue = portfolio.getTotalValue(stock);

        assertEquals(expectedValue, actualValue, 0.01);
    }

    @Test
    void testBankruptcyCheck() {
        // Testas ar teisingai aptinka bankrotą
        Portfolio poorPortfolio = new Portfolio(30.0); // Mažiau nei BANKRUPTCY_THRESHOLD

        assertTrue(poorPortfolio.isBankrupt(stock));
        assertFalse(portfolio.isBankrupt(stock)); // Normalus portfelis
    }

    @Test
    void testTransactionRecording() {
        // Testas ar sandoriai įrašomi
        Transaction transaction = new Transaction(
                org.example.stockmarket.util.TransactionType.BUY,
                10, 50.0, 0.5);

        portfolio.addTransaction(transaction);

        assertEquals(1, portfolio.getTransactions().size());
    }
}
