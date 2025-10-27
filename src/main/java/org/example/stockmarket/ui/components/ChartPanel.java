package org.example.stockmarket.ui.components;

import javafx.geometry.Insets;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.VBox;
import org.example.stockmarket.model.Stock;
import org.example.stockmarket.ui.UIConstants;

import java.util.List;

/**
 * Kainų grafiko panelė
 */
public class ChartPanel extends VBox {
    private final LineChart<Number, Number> priceChart;
    private final XYChart.Series<Number, Number> priceSeries;

    public ChartPanel() {
        super(UIConstants.PADDING_SMALL);
        setPadding(new Insets(UIConstants.PADDING_SMALL));

        // Sukurti line chart
        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel("Ėjimas");
        xAxis.setAutoRanging(false);
        xAxis.setLowerBound(0);
        xAxis.setUpperBound(10);
        xAxis.setTickUnit(1);

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Kaina (EUR)");
        yAxis.setAutoRanging(true);

        priceChart = new LineChart<>(xAxis, yAxis);
        priceChart.setTitle("TECH akcijos kaina");
        priceChart.setCreateSymbols(true);
        priceChart.setLegendVisible(false);
        priceChart.setPrefHeight(UIConstants.CHART_HEIGHT);

        priceSeries = new XYChart.Series<>();
        priceSeries.setName("Kaina");
        priceChart.getData().add(priceSeries);

        getChildren().add(priceChart);
    }

    /**
     * Atnaujina grafiką su nauja kainų istorija
     */
    public void updateChart(Stock stock) {
        priceSeries.getData().clear();
        List<Double> history = stock.getPriceHistory();

        for (int i = 0; i < history.size(); i++) {
            priceSeries.getData().add(new XYChart.Data<>(i, history.get(i)));
        }
    }
}
