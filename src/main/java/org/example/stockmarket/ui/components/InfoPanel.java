package org.example.stockmarket.ui.components;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.example.stockmarket.model.Portfolio;
import org.example.stockmarket.model.Stock;
import org.example.stockmarket.ui.UIConstants;

/**
 * Informacijos panelė - akcijų info ir portfolio
 */
public class InfoPanel extends VBox {
    private final Label stockPriceLabel;
    private final Label priceChangeLabel;
    private final Label balanceLabel;
    private final Label sharesLabel;
    private final Label stockValueLabel;
    private final Label totalValueLabel;

    public InfoPanel() {
        super(UIConstants.PADDING_MEDIUM);
        setPadding(new Insets(UIConstants.PADDING_SMALL, 0, UIConstants.PADDING_SMALL, UIConstants.PADDING_MEDIUM));
        setPrefWidth(UIConstants.INFO_PANEL_WIDTH);

        // Akcijų info
        VBox stockInfo = createStyledBox("Akcijos informacija", Color.web(UIConstants.COLOR_INFO));
        stockPriceLabel = new Label("Kaina: 50.00 EUR");
        stockPriceLabel.setFont(Font.font("System", FontWeight.BOLD, UIConstants.FONT_SIZE_LARGE));
        priceChangeLabel = new Label("Pokytis: -");
        priceChangeLabel.setFont(Font.font("System", UIConstants.FONT_SIZE_NORMAL));
        stockInfo.getChildren().addAll(stockPriceLabel, priceChangeLabel);

        // Portfolio info
        VBox portfolioInfo = createStyledBox("Tavo portfelis", Color.web(UIConstants.COLOR_SUCCESS));
        balanceLabel = new Label("Pinigai: 1000.00 EUR");
        balanceLabel.setFont(Font.font("System", UIConstants.FONT_SIZE_NORMAL));
        sharesLabel = new Label("Akcijų: 0 vnt");
        sharesLabel.setFont(Font.font("System", UIConstants.FONT_SIZE_NORMAL));
        stockValueLabel = new Label("Akcijų vertė: 0.00 EUR");
        stockValueLabel.setFont(Font.font("System", UIConstants.FONT_SIZE_NORMAL));
        totalValueLabel = new Label("Bendras turtas: 1000.00 EUR");
        totalValueLabel.setFont(Font.font("System", FontWeight.BOLD, UIConstants.FONT_SIZE_NORMAL));
        portfolioInfo.getChildren().addAll(balanceLabel, sharesLabel, stockValueLabel,
                                           new Separator(), totalValueLabel);

        getChildren().addAll(stockInfo, portfolioInfo);
    }

    /**
     * Atnaujina akcijų informaciją
     */
    public void updateStockInfo(Stock stock) {
        stockPriceLabel.setText(String.format("Kaina: %.2f EUR", stock.getCurrentPrice()));
        priceChangeLabel.setText("Pokytis: " + stock.getFormattedChange());

        // Nustatyti spalvą pagal pokytį
        if (stock.getFormattedChange().startsWith("+")) {
            priceChangeLabel.setTextFill(Color.web(UIConstants.COLOR_SUCCESS));
        } else if (stock.getFormattedChange().startsWith("-")) {
            priceChangeLabel.setTextFill(Color.web(UIConstants.COLOR_ERROR));
        } else {
            priceChangeLabel.setTextFill(Color.web(UIConstants.COLOR_NEUTRAL));
        }
    }

    /**
     * Atnaujina portfolio informaciją
     */
    public void updatePortfolioInfo(Portfolio portfolio, Stock stock) {
        int shares = portfolio.getShares(stock.getName());
        double stockValue = portfolio.getStockValue(stock);
        double totalValue = portfolio.getTotalValue(stock);

        balanceLabel.setText(String.format("Pinigai: %.2f EUR", portfolio.getBalance()));
        sharesLabel.setText(String.format("Akcijų: %d vnt", shares));
        stockValueLabel.setText(String.format("Akcijų vertė: %.2f EUR", stockValue));
        totalValueLabel.setText(String.format("Bendras turtas: %.2f EUR", totalValue));
    }

    private VBox createStyledBox(String title, Color borderColor) {
        VBox box = new VBox(UIConstants.PADDING_SMALL);
        box.setPadding(new Insets(UIConstants.PADDING_MEDIUM));
        box.setStyle(String.format(
            "-fx-background-color: %s; " +
            "-fx-border-color: %s; " +
            "-fx-border-width: %d; " +
            "-fx-border-radius: %d; " +
            "-fx-background-radius: %d;",
            UIConstants.COLOR_WHITE,
            toRgbString(borderColor),
            UIConstants.BORDER_WIDTH,
            UIConstants.BORDER_RADIUS,
            UIConstants.BORDER_RADIUS
        ));

        Label titleLabel = new Label(title);
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, UIConstants.FONT_SIZE_NORMAL));
        titleLabel.setTextFill(borderColor);

        box.getChildren().add(titleLabel);
        return box;
    }

    private String toRgbString(Color color) {
        return String.format("#%02X%02X%02X",
            (int) (color.getRed() * 255),
            (int) (color.getGreen() * 255),
            (int) (color.getBlue() * 255)
        );
    }
}
