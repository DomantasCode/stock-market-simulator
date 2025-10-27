package org.example.stockmarket.ui;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import org.example.stockmarket.model.GameState;
import org.example.stockmarket.model.Portfolio;
import org.example.stockmarket.model.Stock;
import org.example.stockmarket.util.Constants;
import org.example.stockmarket.util.GameStatus;

/**
 * Pagalbinė klasė dialogų tvarkymui.
 *
 * Atsakinga už:
 * - Sveikinimo dialogo rodymą
 * - Galutinių rezultatų dialogo rodymą
 */
public class DialogHelper {

    /**
     * Parodo sveikinimo dialogą su žaidimo taisyklėmis
     */
    public void showWelcome() {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sveiki atvykę!");
            alert.setHeaderText(UIConstants.HEADER_TITLE);
            alert.setContentText(
                "ŽAIDIMO TIKSLAS:\n" +
                "Prekiauti akcijomis ir uždirbti kuo daugiau per " + Constants.MAX_TRANSACTIONS + " sandorius!\n\n" +
                "PRADŽIOS DUOMENYS:\n" +
                "• Pradinis balansas: " + Constants.INITIAL_BALANCE + " EUR\n" +
                "• Akcija: TECH\n" +
                "• Pradinė kaina: " + Constants.INITIAL_STOCK_PRICE + " EUR\n" +
                "• Kaina keičiasi: ±5% po kiekvieno sandorio\n" +
                "• Sandorio mokestis: 0.1% (tik BUY/SELL)\n\n" +
                "VEIKSMAI:\n" +
                "• BUY - pirkti akcijas\n" +
                "• SELL - parduoti akcijas\n" +
                "• HOLD - praleisti ėjimą\n\n" +
                "SVARBU:\n" +
                "✓ Žaidimas baigiasi po " + Constants.MAX_TRANSACTIONS + " sandorių\n" +
                "✗ Arba jei turtas nukrenta žemiau 50 EUR (bankrotas)\n\n" +
                "Sėkmės!"
            );
            alert.showAndWait();
        });
    }

    /**
     * Parodo galutinius rezultatus ir uždaro programą
     */
    public void showFinalResults(GameState gameState, Stock stock, Portfolio portfolio) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Žaidimas baigtas");
            alert.setHeaderText("🏁 ŽAIDIMAS BAIGTAS");

            StringBuilder content = new StringBuilder();
            content.append(formatGameEndReason(gameState));
            content.append(formatFinalStats(portfolio, stock));
            content.append(formatTransactionStats(portfolio));

            alert.setContentText(content.toString());
            alert.showAndWait();

            // Uždaryti langą
            Platform.exit();
            System.exit(0);
        });
    }

    /**
     * Formatuoja žaidimo pabaigos priežastį
     */
    private String formatGameEndReason(GameState gameState) {
        if (gameState.getStatus() == GameStatus.BANKRUPT) {
            return String.format("💥 BANKROTAS! Jūsų turtas nukrito žemiau %.2f EUR\n\n",
                    Constants.BANKRUPTCY_THRESHOLD);
        } else {
            return String.format("🏁 Žaidimo pabaiga! Atlikti %d sandoriai!\n\n",
                    Constants.MAX_TRANSACTIONS);
        }
    }

    /**
     * Formatuoja galutinę finansinę statistiką
     */
    private String formatFinalStats(Portfolio portfolio, Stock stock) {
        double totalValue = portfolio.getTotalValue(stock);
        double profit = totalValue - Constants.INITIAL_BALANCE;
        double profitPercent = (profit / Constants.INITIAL_BALANCE) * 100;

        return String.format(
            "GALUTINIAI REZULTATAI:\n" +
            "Pradinis balansas: %.2f EUR\n" +
            "Galutinis balansas: %.2f EUR\n" +
            "Akcijų vertė: %.2f EUR\n" +
            "Bendras turtas: %.2f EUR\n" +
            "Pelnas/Nuostolis: %s%.2f EUR (%s%.1f%%)\n",
            (double) Constants.INITIAL_BALANCE,
            portfolio.getBalance(),
            portfolio.getStockValue(stock),
            totalValue,
            profit >= 0 ? "+" : "", profit,
            profitPercent >= 0 ? "+" : "", profitPercent
        );
    }

    /**
     * Formatuoja sandorių statistiką
     */
    private String formatTransactionStats(Portfolio portfolio) {
        int buySellCount = portfolio.getBuySellTransactionCount();
        int totalTransactions = portfolio.getTransactions().size();

        return String.format("\nViso sandorių: %d\nIš jų BUY/SELL: %d",
                totalTransactions, buySellCount);
    }
}
