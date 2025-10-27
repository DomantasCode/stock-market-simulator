package org.example.stockmarket;

import javafx.application.Application;
import javafx.stage.Stage;
import org.example.stockmarket.service.MarketSimulatorGUI;
import org.example.stockmarket.service.strategy.RandomPriceStrategy;
import org.example.stockmarket.ui.GraphicalUI;

/**
 * JavaFX versijos entry point
 * Paleidžia biržos simuliatorių su grafine sąsaja
 */
public class MainGUI extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Sukurti UI
        GraphicalUI ui = new GraphicalUI(primaryStage);

        // Sukurti simuliatorių su Random Price Strategy
        MarketSimulatorGUI simulator = new MarketSimulatorGUI(new RandomPriceStrategy(), ui);

        // Parodyti langą ir paleisti žaidimą
        ui.show();
        simulator.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
