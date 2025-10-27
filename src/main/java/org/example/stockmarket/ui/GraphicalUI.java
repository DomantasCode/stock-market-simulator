package org.example.stockmarket.ui;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.example.stockmarket.model.GameState;
import org.example.stockmarket.model.Portfolio;
import org.example.stockmarket.model.Stock;
import org.example.stockmarket.model.Transaction;
import org.example.stockmarket.ui.components.*;
import org.example.stockmarket.util.Constants;
import org.example.stockmarket.util.GameStatus;

import java.util.concurrent.CompletableFuture;

/**
 * JavaFX grafinė sąsaja biržos simuliatoriui.
 *
 * Ši klasė sukuria ir valdo visą grafinę vartotojo sąsają, įskaitant:
 * - Kainų grafiką (LineChart)
 * - Portfolio informaciją
 * - Valdymo mygtukus (PIRKTI, PARDUOTI, PRALEISTI)
 * - Pranešimų zoną
 *
 * Visi UI elementų stiliai ir spalvos apibrėžti {@link UIConstants} klasėje.
 * UI komponentai išskaidyti į atskiras klases {@link org.example.stockmarket.ui.components} pakete.
 *
 * @see MarketSimulatorGUI
 * @see UIConstants
 */
public class GraphicalUI {
    private final Stage stage;
    private Scene scene;

    // UI komponentai
    private HeaderPanel headerPanel;
    private ChartPanel chartPanel;
    private InfoPanel infoPanel;
    private ControlPanel controlPanel;
    private DialogHelper dialogHelper;

    public GraphicalUI(Stage stage) {
        this.stage = stage;
        this.stage.setTitle(UIConstants.APP_TITLE);
        this.dialogHelper = new DialogHelper();
        createUI();
    }

    /**
     * Sukuria pagrindinę UI struktūrą
     */
    private void createUI() {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(UIConstants.PADDING_MEDIUM));
        root.setStyle("-fx-background-color: " + UIConstants.COLOR_BACKGROUND + ";");

        // Sukurti komponentus
        headerPanel = new HeaderPanel();
        chartPanel = new ChartPanel();
        infoPanel = new InfoPanel();
        controlPanel = new ControlPanel();

        // Sudėlioti komponentus
        root.setTop(headerPanel);
        root.setCenter(chartPanel);
        root.setRight(infoPanel);
        root.setBottom(controlPanel);

        scene = new Scene(root, UIConstants.WINDOW_WIDTH, UIConstants.WINDOW_HEIGHT);
        stage.setScene(scene);
        stage.setOnCloseRequest(e -> {
            Platform.exit();
            System.exit(0);
        });
    }

    /**
     * Parodo welcome dialogą
     */
    public void showWelcome() {
        dialogHelper.showWelcome();
    }

    /**
     * Atnaujina žaidimo būseną UI
     */
    public void updateGameState(GameState gameState, Stock stock, Portfolio portfolio) {
        Platform.runLater(() -> {
            // Atnaujinti sandorių skaičių
            int transactionCount = portfolio.getTransactions().size();
            headerPanel.updateTransactionCount(transactionCount, Constants.MAX_TRANSACTIONS);

            // Atnaujinti akcijų ir portfolio info
            infoPanel.updateStockInfo(stock);
            infoPanel.updatePortfolioInfo(portfolio, stock);

            // Atnaujinti grafiką
            chartPanel.updateChart(stock);
        });
    }

    /**
     * Gauna vartotojo komandą
     */
    public CompletableFuture<String> getUserCommand() {
        return controlPanel.getUserCommand();
    }

    /**
     * Parodo sandorio sėkmės pranešimą
     */
    public void showTransactionSuccess(Transaction transaction) {
        Platform.runLater(() -> controlPanel.showTransactionSuccess(transaction));
    }

    /**
     * Parodo klaidos pranešimą
     */
    public void showError(String message) {
        Platform.runLater(() -> controlPanel.showError(message));
    }

    /**
     * Parodo pranešimą
     */
    public void showMessage(String message) {
        Platform.runLater(() -> controlPanel.showMessage(message));
    }

    /**
     * Parodo "Rodyti rezultatus" mygtuką
     */
    public void showResultsButton() {
        Platform.runLater(() -> controlPanel.showResultsButton());
    }

    /**
     * Laukia "Rodyti rezultatus" mygtuko paspaudimo
     */
    public CompletableFuture<Void> waitForShowResults() {
        return controlPanel.waitForShowResults();
    }

    /**
     * Parodo langą
     */
    public void show() {
        stage.show();
    }

    /**
     * Parodo galutinius rezultatus
     */
    public void showFinalResults(GameState gameState, Stock stock, Portfolio portfolio) {
        dialogHelper.showFinalResults(gameState, stock, portfolio);
    }
}
