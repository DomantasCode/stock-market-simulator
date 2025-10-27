package org.example.stockmarket.ui;

/**
 * UI konstantos - spalvos, dydžiai ir kiti UI parametrai
 * Centralizuoja visus magic strings ir numbers iš GraphicalUI
 */
public class UIConstants {

    // === SPALVOS ===

    // Pagrindinės spalvos
    public static final String COLOR_PRIMARY = "#2c3e50";        // Tamsiai mėlyna
    public static final String COLOR_SECONDARY = "#34495e";      // Pilka-mėlyna
    public static final String COLOR_BACKGROUND = "#f5f5f5";     // Šviesiai pilka
    public static final String COLOR_WHITE = "white";

    // Akcijų spalvos
    public static final String COLOR_BUY = "#27ae60";           // Žalia (pirkimas)
    public static final String COLOR_SELL = "#e74c3c";          // Raudona (pardavimas)
    public static final String COLOR_HOLD = "#95a5a6";          // Pilka (laukimas)
    public static final String COLOR_INFO = "#3498db";          // Mėlyna (informacija)

    // Spalvos būsenoms
    public static final String COLOR_SUCCESS = "#27ae60";       // Žalia (sėkmė)
    public static final String COLOR_ERROR = "#e74c3c";         // Raudona (klaida)
    public static final String COLOR_NEUTRAL = "#7f8c8d";       // Pilka (neutralus)

    // === DYDŽIAI ===

    // Lango dydžiai
    public static final int WINDOW_WIDTH = 1200;
    public static final int WINDOW_HEIGHT = 700;

    // Šriftų dydžiai
    public static final int FONT_SIZE_TITLE = 28;
    public static final int FONT_SIZE_SUBTITLE = 18;
    public static final int FONT_SIZE_NORMAL = 14;
    public static final int FONT_SIZE_LARGE = 16;
    public static final int FONT_SIZE_SMALL = 12;

    // Mygtukų dydžiai
    public static final int BUTTON_WIDTH = 120;
    public static final int BUTTON_HEIGHT = 40;
    public static final int BUTTON_LARGE_WIDTH = 200;

    // Panelių dydžiai
    public static final int INFO_PANEL_WIDTH = 300;
    public static final int MESSAGE_AREA_HEIGHT = 100;
    public static final int CHART_HEIGHT = 400;
    public static final int QUANTITY_FIELD_WIDTH = 100;

    // Tarpai (padding, margin)
    public static final int PADDING_SMALL = 10;
    public static final int PADDING_MEDIUM = 15;
    public static final int BORDER_WIDTH = 2;
    public static final int BORDER_RADIUS = 5;

    // === TEKSTAI ===

    public static final String APP_TITLE = "📈 Biržos Mini-Simuliatorius";
    public static final String HEADER_TITLE = "📈 BIRŽOS MINI-SIMULIATORIUS";

    // Mygtukų tekstai
    public static final String BUTTON_BUY = "PIRKTI";
    public static final String BUTTON_SELL = "PARDUOTI";
    public static final String BUTTON_HOLD = "PRALEISTI";
    public static final String BUTTON_SHOW_RESULTS = "RODYTI REZULTATUS";

    // Uždrausti instantiation
    private UIConstants() {
        throw new UnsupportedOperationException("Utility class");
    }
}
