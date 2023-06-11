package utility.customGui;

import java.awt.Color;

/**
 * Represents a color gradient with a dark and light color.
 */
public class Colors {
    private final Color dark;
    private final Color light;

    /**
     * Creates a ColorGradient with the specified dark and light colors.
     *
     * @param dark  The dark color.
     * @param light The light color.
     */
    public Colors(Color dark, Color light) {
        this.dark = dark;
        this.light = light;
    }

    /**
     * Returns the dark color of the gradient.
     *
     * @return The dark color.
     */
    public Color getDarkColor() {
        return dark;
    }

    /**
     * Returns the light color of the gradient.
     *
     * @return The light color.
     */
    public Color getLightColor() {
        return light;
    }

    /**
     * Returns the color of the gradient.
     *
     * @param light Determines if the light color should be returned.
     * @return The color of the gradient.
     */
    public Color getColor(final boolean light) {
        return light ? getLightColor() : getDarkColor();
    }

    // Predefined color gradients

    /**
     * Predefined color gradient for the "FIELD" gradient.
     */
    public static final Colors FIELD = new Colors(
            new Color(118, 150, 86, 255),
            new Color(238, 238, 210, 255));

    /**
     * Predefined color gradient for the "HIGHLIGHT" gradient.
     */
    public static final Colors HIGHLIGHT = new Colors(
            new Color(187, 203, 43, 255),
            new Color(247, 247, 105, 255));

    /**
     * Predefined color gradient for the "MOVE" gradient.
     */
    public static final Colors MOVE = new Colors(
            new Color(228, 167, 17, 255),
            new Color(252, 183, 43, 255));

    /**
     * Predefined color gradient for the "ATTACK" gradient.
     */
    public static final Colors ATTACK = new Colors(
            new Color(212, 108, 81, 255),
            new Color(236, 125, 106, 255));

    /**
     * Predefined color gradient for the "GREEN" gradient.
     */
    public static final Colors OTHER = new Colors(
            new Color(90, 170, 193, 255),
            new Color(114, 189, 218, 255));
    /**
     * Predefined color gradient for the "DARK" gradient.
     */
    public static final Colors DARK = new Colors(
            new Color(39, 36, 33, 255),
            new Color(49, 46, 43, 255));
}