package org.example.stockmarket.ui.components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.control.Label;
import org.example.stockmarket.ui.UIConstants;

/**
 * Antraštės panelė su pavadinimu ir sandorių skaitikliu
 */
public class HeaderPanel extends VBox {
    private final Label turnLabel;

    public HeaderPanel() {
        super(UIConstants.PADDING_SMALL);
        setPadding(new Insets(0, 0, UIConstants.PADDING_MEDIUM, 0));

        Text title = new Text(UIConstants.HEADER_TITLE);
        title.setFont(Font.font("System", FontWeight.BOLD, UIConstants.FONT_SIZE_TITLE));
        title.setFill(Color.web(UIConstants.COLOR_PRIMARY));

        turnLabel = new Label("Sandoriai: 0/10");
        turnLabel.setFont(Font.font("System", FontWeight.SEMI_BOLD, UIConstants.FONT_SIZE_SUBTITLE));
        turnLabel.setTextFill(Color.web(UIConstants.COLOR_SECONDARY));

        getChildren().addAll(title, turnLabel);
        setAlignment(Pos.CENTER);
    }

    /**
     * Atnaujina sandorių skaičių
     */
    public void updateTransactionCount(int current, int max) {
        turnLabel.setText(String.format("Sandoriai: %d/%d", current, max));
    }
}
