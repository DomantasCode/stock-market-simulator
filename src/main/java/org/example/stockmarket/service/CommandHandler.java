package org.example.stockmarket.service;

import org.example.stockmarket.model.Portfolio;
import org.example.stockmarket.model.Stock;
import org.example.stockmarket.model.Transaction;
import org.example.stockmarket.ui.GraphicalUI;

/**
 * Apdoroja vartotojo komandas (BUY, SELL, HOLD).
 *
 * Ši klasė atsakinga už:
 * - Komandų validavimą
 * - Sandorių vykdymą
 * - Pranešimų rodymą vartotojui
 */
public class CommandHandler {
    private final Portfolio portfolio;
    private final Stock stock;
    private final GraphicalUI ui;

    public CommandHandler(Portfolio portfolio, Stock stock, GraphicalUI ui) {
        this.portfolio = portfolio;
        this.stock = stock;
        this.ui = ui;
    }

    /**
     * Apdoroja vartotojo komandą
     * @param command Vartotojo įvesta komanda
     * @return true jei komanda sėkminga, false jei klaida
     */
    public boolean processCommand(String command) {
        String[] parts = command.trim().toUpperCase().split("\\s+");

        if (parts.length == 0 || parts[0].isEmpty()) {
            ui.showError("Tuščia komanda! Naudokite: BUY <kiekis>, SELL <kiekis>, HOLD");
            return false;
        }

        String action = parts[0];

        try {
            switch (action) {
                case "BUY":
                    return handleBuy(parts);
                case "SELL":
                    return handleSell(parts);
                case "HOLD":
                    return handleHold();
                default:
                    ui.showError("Nežinoma komanda! Naudokite: BUY <kiekis>, SELL <kiekis>, HOLD");
                    return false;
            }
        } catch (NumberFormatException e) {
            ui.showError("Neteisingas kiekis! Įveskite skaičių.");
            return false;
        } catch (ArrayIndexOutOfBoundsException e) {
            ui.showError("Trūksta parametrų! Pvz: BUY 5");
            return false;
        }
    }

    /**
     * Apdoroja BUY komandą
     */
    private boolean handleBuy(String[] parts) {
        int quantity = Integer.parseInt(parts[1]);

        TransactionValidator.ValidationResult result =
                TransactionValidator.validateBuy(portfolio, stock, quantity);

        if (!result.isValid()) {
            ui.showError(result.getErrorMessage());
            return false;
        }

        Transaction transaction = TransactionFactory.createBuyTransaction(
                quantity, stock.getCurrentPrice());
        portfolio.buy(stock, quantity, transaction.getFee());
        portfolio.addTransaction(transaction);

        ui.showTransactionSuccess(transaction);
        return true;
    }

    /**
     * Apdoroja SELL komandą
     */
    private boolean handleSell(String[] parts) {
        int quantity = Integer.parseInt(parts[1]);

        TransactionValidator.ValidationResult result =
                TransactionValidator.validateSell(portfolio, stock, quantity);

        if (!result.isValid()) {
            ui.showError(result.getErrorMessage());
            return false;
        }

        Transaction transaction = TransactionFactory.createSellTransaction(
                quantity, stock.getCurrentPrice());
        portfolio.sell(stock, quantity, transaction.getFee());
        portfolio.addTransaction(transaction);

        ui.showTransactionSuccess(transaction);
        return true;
    }

    /**
     * Apdoroja HOLD komandą
     */
    private boolean handleHold() {
        Transaction transaction = TransactionFactory.createHoldTransaction();
        portfolio.addTransaction(transaction);
        ui.showMessage("Praleidžiate ėjimą...");
        return true;
    }
}
