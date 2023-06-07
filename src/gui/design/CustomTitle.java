package gui.design;

import util.ColorGradient;

import javax.swing.*;

/**
 * TitleLabel is a customized JLabel used for displaying titles with consistent styling.
 */
public class CustomTitle extends JLabel {

    /**
     * Initialize the title label with consistent styling.
     */
    private void setupTitleLabel() {
        setForeground(ColorGradient.GREEN.getColor(false));
        setHorizontalAlignment(SwingConstants.CENTER);
        setFont(Fonts.Default.TITLE);
    }

    /**
     * Create a TitleLabel without text.
     */
    public CustomTitle() {
        setupTitleLabel();
    }

    /**
     * Create a TitleLabel with text.
     *
     * @param text the text to display as the title
     */
    public CustomTitle(String text) {
        super(text);
        setupTitleLabel();
    }

    /**
     * Create a TitleLabel with text and position.
     *
     * @param text the text to display as the title
     * @param x    the x-coordinate of the title label position
     * @param y    the y-coordinate of the title label position
     */
    public CustomTitle(String text, int x, int y) {
        super(text);
        setBounds(x, y, 120, 20);
        setupTitleLabel();
    }

    /**
     * Create a TitleLabel with text, position, and size.
     *
     * @param text   the text to display as the title
     * @param x      the x-coordinate of the title label position
     * @param y      the y-coordinate of the title label position
     * @param width  the width of the title label
     * @param height the height of the title label
     */
    public CustomTitle(String text, int x, int y, int width, int height) {
        super(text);
        setBounds(x, y, width, height);
        setupTitleLabel();
    }
}