package gui.design;

import util.ColorGradient;

import javax.swing.*;

/**
 * CustomLabel is a customized JLabel with consistent styling and initialization.
 */
public class CustomLabel extends JLabel {

    /**
     * Initialize the label with consistent styling.
     */
    private void setupLabel() {
        setForeground(ColorGradient.GREEN.getColor(false));
        setFont(Fonts.Components.LABEL);
    }

    /**
     * Create a CustomLabel without text.
     */
    public CustomLabel() {
        setupLabel();
    }

    /**
     * Create a CustomLabel with text.
     * @param text the text to display on the label
     */
    public CustomLabel(String text) {
        super(text);
        setupLabel();
    }

    /**
     * Create a CustomLabel with text and position.
     * @param text the text to display on the label
     * @param x the x-coordinate of the label position
     * @param y the y-coordinate of the label position
     */
    public CustomLabel(String text, int x, int y) {
        super(text);
        setBounds(x, y, 120, 20);
        setupLabel();
    }

    /**
     * Create a CustomLabel with text, position, and size.
     * @param text the text to display on the label
     * @param x the x-coordinate of the label position
     * @param y the y-coordinate of the label position
     * @param width the width of the label
     * @param height the height of the label
     */
    public CustomLabel(String text, int x, int y, int width, int height) {
        super(text);
        setBounds(x, y, width, height);
        setupLabel();
    }
}
