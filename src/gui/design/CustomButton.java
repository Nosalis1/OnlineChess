package gui.design;

import util.ColorGradient;

import javax.swing.*;
import java.awt.event.ActionListener;

/**
 * CustomButton is a customized JButton with consistent styling and initialization.
 */
public class CustomButton extends JButton {

    /**
     * Initialize the button with consistent styling.
     */
    private void setupButton() {
        setForeground(ColorGradient.GREEN.getColor(false));
        setBackground(ColorGradient.DARK.getColor(false));
        setFont(Fonts.Components.BUTTON);
    }

    /**
     * Create a CustomButton without text.
     */
    public CustomButton() {
        setupButton();
    }

    /**
     * Create a CustomButton with text.
     * @param text the text to display on the button
     */
    public CustomButton(String text) {
        super(text);
        setupButton();
    }

    /**
     * Create a CustomButton with text and position.
     * @param text the text to display on the button
     * @param x the x-coordinate of the button position
     * @param y the y-coordinate of the button position
     */
    public CustomButton(String text, int x, int y) {
        super(text);
        setBounds(x, y, 120, 20);
        setupButton();
    }

    /**
     * Create a CustomButton with text, position, and size.
     * @param text the text to display on the button
     * @param x the x-coordinate of the button position
     * @param y the y-coordinate of the button position
     * @param width the width of the button
     * @param height the height of the button
     */
    public CustomButton(String text, int x, int y, int width, int height) {
        super(text);
        setBounds(x, y, width, height);
        setupButton();
    }

    /**
     * Create a CustomButton with an ActionListener.
     * @param l the ActionListener for the button
     */
    public CustomButton(ActionListener l) {
        super();
        addActionListener(l);
        setupButton();
    }

    /**
     * Create a CustomButton with text and an ActionListener.
     * @param text the text to display on the button
     * @param l the ActionListener for the button
     */
    public CustomButton(String text, ActionListener l) {
        super(text);
        addActionListener(l);
        setupButton();
    }

    /**
     * Create a CustomButton with text, position, and an ActionListener.
     * @param text the text to display on the button
     * @param x the x-coordinate of the button position
     * @param y the y-coordinate of the button position
     * @param l the ActionListener for the button
     */
    public CustomButton(String text, int x, int y, ActionListener l) {
        super(text);
        setBounds(x, y, 120, 20);
        addActionListener(l);
        setupButton();
    }

    /**
     * Create a CustomButton with text, position, size, and an ActionListener.
     * @param text the text to display on the button
     * @param x the x-coordinate of the button position
     * @param y the y-coordinate of the button position
     * @param width the width of the button
     * @param height the height of the button
     * @param l the ActionListener for the button
     */
    public CustomButton(String text, int x, int y, int width, int height, ActionListener l) {
        super(text);
        setBounds(x, y, width, height);
        addActionListener(l);
        setupButton();
    }
}
