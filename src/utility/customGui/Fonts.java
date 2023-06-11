package utility.customGui;

import java.awt.*;

/**
 * The Fonts class provides constants for commonly used fonts in the GUI of a chess game.
 */
public abstract class Fonts {

    /**
     * Default fonts for titles and text.
     */
    public static class Default {
        /**
         * The default font for titles.
         */
        public static final Font TITLE = new Font("Arial", Font.BOLD, 24);

        /**
         * The default font for text.
         */
        @SuppressWarnings("unused")
        public static final Font TEXT = new Font("Arial", Font.PLAIN, 16);
    }

    /**
     * Fonts for specific GUI components.
     */
    public static class Components {
        /**
         * The font for buttons.
         */
        public static final Font BUTTON = new Font("Arial", Font.BOLD, 16);

        /**
         * The font for labels.
         */
        public static final Font LABEL = new Font("Arial", Font.PLAIN, 16);

        /**
         * The font for text fields.
         */
        public static final Font TEXT_FIELD = new Font("Arial", Font.PLAIN, 14);

        /**
         * The font for password fields.
         */
        public static final Font PASSWORD_FIELD = new Font("Arial", Font.PLAIN, 14);
    }
}