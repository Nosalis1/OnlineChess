package util;

import java.awt.Color;

/**
 * Represents a color gradient with a dark and light color.
 */
public class ColorGradient {
    private final Color dark;
    private final Color light;

    /**
     * Creates a ColorGradient with the specified dark and light colors.
     *
     * @param dark  The dark color.
     * @param light The light color.
     */
    public ColorGradient(Color dark, Color light) {
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
    public static final ColorGradient FIELD = new ColorGradient(
            new Color(122, 148, 87, 255),
            new Color(238, 238, 212, 255));

    /**
     * Predefined color gradient for the "HIGHLIGHT" gradient.
     */
    public static final ColorGradient HIGHLIGHT = new ColorGradient(
            new Color(90, 170, 193, 255),
            new Color(114, 189, 218, 255));

    /**
     * Predefined color gradient for the "MOVE" gradient.
     */
    public static final ColorGradient MOVE = new ColorGradient(
            new Color(228, 167, 17, 255),
            new Color(252, 183, 43, 255));

    /**
     * Predefined color gradient for the "ATTACK" gradient.
     */
    public static final ColorGradient ATTACK = new ColorGradient(
            new Color(212, 108, 81, 255),
            new Color(237, 126, 106, 255));

    /**
     * Predefined color gradient for the "GREEN" gradient.
     */
    public static final ColorGradient GREEN = new ColorGradient(
            new Color(162, 195, 88, 255),
            new Color(188, 212, 114, 255));

    /**
     * Predefined color gradient for the "DARK" gradient.
     */
    public static final ColorGradient DARK = new ColorGradient(
            new Color(39, 36, 33, 255),
            new Color(49, 46, 43, 255));
}