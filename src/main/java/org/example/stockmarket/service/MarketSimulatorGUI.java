package org.example.stockmarket.service;

import javafx.application.Platform;
import org.example.stockmarket.model.*;
import org.example.stockmarket.service.strategy.PriceStrategy;
import org.example.stockmarket.ui.GraphicalUI;
import org.example.stockmarket.util.Constants;
import org.example.stockmarket.util.TransactionType;

import java.util.concurrent.CompletableFuture;

/**
 * GUI versija žaidimo simuliatoriaus.
 *
 * Ši klasė valdo žaidimo ciklą GUI režimu, asinchroniškai apdorodama
 * vartotojo įvestis ir atnaujindama grafinę sąsają. Žaidimas trunka
 * 10 sandorių arba iki bankroto.
 *
 * @see GraphicalUI
 * @see Portfolio
 * @see Stock
 */
public class MarketSimulatorGUI {
    private final Stock stock;
    private final Portfolio portfolio;
    private final GameState gameState;
    private final PriceStrategy priceStrategy;
    private final GraphicalUI ui;
    private final CommandHandler commandHandler;

    public MarketSimulatorGUI(PriceStrategy priceStrategy, GraphicalUI ui) {
        this.stock = new Stock(Constants.STOCK_NAME, Constants.INITIAL_STOCK_PRICE);
        this.portfolio = new Portfolio(Constants.INITIAL_BALANCE);
        this.gameState = new GameState();
        this.priceStrategy = priceStrategy;
        this.ui = ui;
        this.commandHandler = new CommandHandler(portfolio, stock, ui);
    }

    /**
     * Paleidžia žaidimą GUI režimu
     */
    public void start() {
        ui.showWelcome();

        // Paleisti žaidimo ciklą asinchroniškai
        new Thread(() -> {
            try {
                Thread.sleep(500); // Trumpa pauze prieš pradedant

                while (!gameState.isGameOver()) {
                    playTurn();
                }

                // Po 10 ėjimų - 11-tas ėjimas su "Rodyti rezultatus" mygtuku
                ui.showResultsButton();
                ui.showMessage("Žaidimas baigtas! Paspauskite mygtuką, kad pamatytumėte rezultatus.");

                // Laukti kol vartotojas paspaus "Rodyti rezultatus"
                ui.waitForShowResults().join();

                showFinalResults();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * Vienas žaidimo ėjimas
     */
    private void playTurn() throws InterruptedException {
        ui.updateGameState(gameState, stock, portfolio);

        // Kartoti kol įves teisingą komandą
        boolean validCommand = false;
        while (!validCommand) {
            CompletableFuture<String> commandFuture = ui.getUserCommand();
            String command = commandFuture.join(); // Laukti vartotojo įvesties
            validCommand = commandHandler.processCommand(command);
        }

        // Po sandorio atnaujinti rinką (kaina keičiasi po kiekvieno sandorio)
        updateMarket();

        // Po sandorio atnaujinti UI kad rodytų naują sandorių skaičių ir kainą
        ui.updateGameState(gameState, stock, portfolio);

        // Tikrinti ar pasiektas sandorių limitas
        checkTransactionLimit();

        // Tikrinti bankrotą ir pereiti į kitą ėjimą
        checkBankruptcy();
        if (!gameState.isGameOver()) {
            gameState.nextTurn();
        }
    }

    /**
     * Atnaujina rinkos kainas
     */
    private void updateMarket() {
        double newPrice = priceStrategy.generateNextPrice(stock.getCurrentPrice());
        stock.updatePrice(newPrice);
    }

    /**
     * Tikrina ar žaidėjas bankrutavo
     */
    private void checkBankruptcy() {
        if (portfolio.isBankrupt(stock)) {
            gameState.setBankrupt();
        }
    }

    /**
     * Tikrina ar pasiektas maksimalus sandorių skaičius
     */
    private void checkTransactionLimit() {
        if (portfolio.getTransactions().size() >= Constants.MAX_TRANSACTIONS) {
            gameState.setFinished();
        }
    }

    /**
     * Rodo galutinius rezultatus
     */
    private void showFinalResults() {
        ui.showFinalResults(gameState, stock, portfolio);
    }
}
