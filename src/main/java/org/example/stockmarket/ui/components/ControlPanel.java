package org.example.stockmarket.ui.components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.example.stockmarket.model.Transaction;
import org.example.stockmarket.ui.UIConstants;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

/**
 * Valdymo panelė - mygtukai, input ir pranešimai
 */
public class ControlPanel extends VBox {
    private final TextField quantityField;
    private final Button buyButton;
    private final Button sellButton;
    private final Button holdButton;
    private final Button showResultsButton;
    private final HBox tradingButtonsBox;
    private final TextArea messageArea;

    private CompletableFuture<String> commandFuture;
    private CompletableFuture<Void> showResultsFuture;

    public ControlPanel() {
        super(UIConstants.PADDING_MEDIUM);
        setPadding(new Insets(UIConstants.PADDING_MEDIUM, 0, 0, 0));

        // Valdymo mygtukai (prekybai)
        tradingButtonsBox = new HBox(UIConstants.PADDING_SMALL);
        tradingButtonsBox.setAlignment(Pos.CENTER);

        Label quantityLabel = new Label("Kiekis:");
        quantityLabel.setFont(Font.font("System", UIConstants.FONT_SIZE_NORMAL));

        quantityField = new TextField();
        quantityField.setPromptText("Įveskite kiekį");
        quantityField.setPrefWidth(UIConstants.QUANTITY_FIELD_WIDTH);

        buyButton = createStyledButton(UIConstants.BUTTON_BUY, UIConstants.COLOR_BUY);
        sellButton = createStyledButton(UIConstants.BUTTON_SELL, UIConstants.COLOR_SELL);
        holdButton = createStyledButton(UIConstants.BUTTON_HOLD, UIConstants.COLOR_HOLD);

        buyButton.setOnAction(e -> handleCommand("BUY"));
        sellButton.setOnAction(e -> handleCommand("SELL"));
        holdButton.setOnAction(e -> handleCommand("HOLD"));

        tradingButtonsBox.getChildren().addAll(quantityLabel, quantityField, buyButton, sellButton, holdButton);

        // "Rodyti rezultatus" mygtukas
        showResultsButton = createStyledButton(UIConstants.BUTTON_SHOW_RESULTS, UIConstants.COLOR_INFO);
        showResultsButton.setPrefWidth(UIConstants.BUTTON_LARGE_WIDTH);
        showResultsButton.setOnAction(e -> handleShowResults());
        showResultsButton.setVisible(false);
        showResultsButton.setManaged(false);

        // Konteineris mygtukams
        VBox buttonsContainer = new VBox(UIConstants.PADDING_SMALL);
        buttonsContainer.setAlignment(Pos.CENTER);
        buttonsContainer.getChildren().addAll(tradingButtonsBox, showResultsButton);

        // Pranešimų zona
        Label messagesLabel = new Label("Pranešimai:");
        messagesLabel.setFont(Font.font("System", FontWeight.BOLD, UIConstants.FONT_SIZE_SMALL));

        messageArea = new TextArea();
        messageArea.setEditable(false);
        messageArea.setPrefHeight(UIConstants.MESSAGE_AREA_HEIGHT);
        messageArea.setWrapText(true);
        messageArea.setStyle("-fx-control-inner-background: #ecf0f1;");

        getChildren().addAll(buttonsContainer, messagesLabel, messageArea);
    }

    /**
     * Laukia vartotojo komandos
     */
    public CompletableFuture<String> getUserCommand() {
        commandFuture = new CompletableFuture<>();
        return commandFuture;
    }

    /**
     * Laukia "Rodyti rezultatus" mygtuko
     */
    public CompletableFuture<Void> waitForShowResults() {
        showResultsFuture = new CompletableFuture<>();
        return showResultsFuture;
    }

    /**
     * Parodo "Rodyti rezultatus" mygtuką
     */
    public void showResultsButton() {
        tradingButtonsBox.setVisible(false);
        tradingButtonsBox.setManaged(false);
        showResultsButton.setVisible(true);
        showResultsButton.setManaged(true);
    }

    /**
     * Parodo sandorio sėkmės pranešimą
     */
    public void showTransactionSuccess(Transaction transaction) {
        String message = "✓ Sandoris atliktas: " + transaction.toString();
        appendMessage(message);
    }

    /**
     * Parodo klaidos pranešimą
     */
    public void showError(String message) {
        appendMessage("✗ KLAIDA: " + message);
    }

    /**
     * Parodo pranešimą
     */
    public void showMessage(String message) {
        appendMessage(message);
    }

    private void handleCommand(String action) {
        String command;
        if (action.equals("HOLD")) {
            command = "HOLD";
        } else {
            String quantity = quantityField.getText().trim();
            if (quantity.isEmpty()) {
                showError("Įveskite kiekį!");
                return;
            }
            command = action + " " + quantity;
        }

        if (commandFuture != null && !commandFuture.isDone()) {
            commandFuture.complete(command);
        }

        quantityField.clear();
    }

    private void handleShowResults() {
        if (showResultsFuture != null && !showResultsFuture.isDone()) {
            showResultsFuture.complete(null);
        }
    }

    private void appendMessage(String message) {
        messageArea.appendText(message + "\n");
        messageArea.setScrollTop(Double.MAX_VALUE);
    }

    private Button createStyledButton(String text, String color) {
        Button button = new Button(text);
        button.setFont(Font.font("System", FontWeight.BOLD, UIConstants.FONT_SIZE_NORMAL));
        button.setPrefWidth(UIConstants.BUTTON_WIDTH);
        button.setPrefHeight(UIConstants.BUTTON_HEIGHT);
        button.setStyle(String.format(
            "-fx-background-color: %s; " +
            "-fx-text-fill: white; " +
            "-fx-background-radius: %d; " +
            "-fx-cursor: hand;",
            color, UIConstants.BORDER_RADIUS
        ));

        // Hover effect
        button.setOnMouseEntered(e -> button.setOpacity(0.8));
        button.setOnMouseExited(e -> button.setOpacity(1.0));

        return button;
    }
}
