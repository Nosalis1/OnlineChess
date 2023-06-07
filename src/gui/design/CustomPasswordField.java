package gui.design;

import util.ColorGradient;

import javax.swing.*;

/**
 * CustomPasswordField is a customized JPasswordField used for password input fields with consistent styling.
 */
public class CustomPasswordField extends JPasswordField {

    /**
     * Initialize the password field with consistent styling.
     */
    private void setupPasswordField() {
        setForeground(ColorGradient.GREEN.getColor(true));
        setBackground(ColorGradient.DARK.getColor(false));
        setFont(Fonts.Components.PASSWORD_FIELD);
        setHorizontalAlignment(SwingConstants.CENTER);
    }

    /**
     * Create a CustomPasswordField with default styling.
     */
    public CustomPasswordField() {
        setupPasswordField();
    }

    /**
     * Create a CustomPasswordField with default styling and set its position.
     *
     * @param x The x-coordinate of the password field's position
     * @param y The y-coordinate of the password field's position
     */
    public CustomPasswordField(int x, int y) {
        setBounds(x, y, 120, 20);
        setupPasswordField();
    }

    /**
     * Create a CustomPasswordField with default styling and set its position and size.
     *
     * @param x      The x-coordinate of the password field's position
     * @param y      The y-coordinate of the password field's position
     * @param width  The width of the password field
     * @param height The height of the password field
     */
    public CustomPasswordField(int x, int y, int width, int height) {
        setBounds(x, y, width, height);
        setupPasswordField();
    }
}