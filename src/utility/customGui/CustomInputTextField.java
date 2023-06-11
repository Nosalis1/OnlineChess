package utility.customGui;

import javax.swing.*;

/**
 * CustomInputTextField is a customized JTextField used for input fields with consistent styling.
 */
@SuppressWarnings("unused")
public class CustomInputTextField extends JTextField {

    /**
     * Initialize the input field with consistent styling.
     */
    private void setup() {
        setForeground(Colors.GREEN.getColor(true));
        setBackground(Colors.DARK.getColor(false));
        setFont(Fonts.Components.TEXT_FIELD);
        setHorizontalAlignment(SwingConstants.CENTER);
    }

    /**
     * Create a CustomInputTextField with default styling.
     */
    public CustomInputTextField() {
        setup();
    }

    /**
     * Create a CustomInputTextField with default styling and set its position.
     *
     * @param x The x-coordinate of the input field's position
     * @param y The y-coordinate of the input field's position
     */
    public CustomInputTextField(int x, int y) {
        setBounds(x, y, 120, 20);
        setup();
    }

    /**
     * Create a CustomInputTextField with default styling and set its position and size.
     *
     * @param x      The x-coordinate of the input field's position
     * @param y      The y-coordinate of the input field's position
     * @param width  The width of the input field
     * @param height The height of the input field
     */
    public CustomInputTextField(int x, int y, int width, int height) {
        setBounds(x, y, width, height);
        setup();
    }
}