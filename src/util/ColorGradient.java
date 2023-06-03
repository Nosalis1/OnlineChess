package util;

import java.awt.Color;

/**

 The ColorGradient class represents a gradient between two colors.

 It provides methods to retrieve the colors based on a gradient flag.
 */
public class ColorGradient {
    private final Color dark;
    private final Color light;

    /**
     * Constructs a ColorGradient object with the specified dark and light colors.
     *
     * @param dark  the darker color in the gradient
     * @param light the lighter color in the gradient
     */
    public ColorGradient(Color dark, Color light) {
        this.dark = dark;
        this.light = light;
    }

    /**
     * Retrieves the color based on the gradient flag.
     *
     * @param isLight a flag indicating whether to retrieve the lighter color (true) or the darker color (false)
     * @return the color based on the gradient flag
     */
    public final Color getColor(boolean isLight) {
        return isLight ? light : dark;
    }

    // Predefined color gradients

    /**
     * A predefined color gradient for the FIELD.
     * The dark color is (122, 148, 87, 255), and the light color is (238, 238, 212, 255).
     */
    public static final ColorGradient FIELD = new ColorGradient(
            new Color(122, 148, 87, 255),
            new Color(238, 238, 212, 255));

    /**
     * A predefined color gradient for the HIGHLIGHT.
     * The dark color is (90, 170, 193, 255), and the light color is (114, 189, 218, 255).
     */
    public static final ColorGradient HIGHLIGHT = new ColorGradient(
            new Color(90, 170, 193, 255),
            new Color(114, 189, 218, 255));

    /**
     * A predefined color gradient for the MOVE.
     * The dark color is (228, 167, 17, 255), and the light color is (252, 183, 43, 255).
     */
    public static final ColorGradient MOVE = new ColorGradient(
            new Color(228, 167, 17, 255),
            new Color(252, 183, 43, 255));

    /**
     * A predefined color gradient for the ATTACK.
     * The dark color is (212, 108, 81, 255), and the light color is (237, 126, 106, 255).
     */
    public static final ColorGradient ATTACK = new ColorGradient(
            new Color(212, 108, 81, 255),
            new Color(237, 126, 106, 255));

    /**
     * A predefined color gradient for the GREEN.
     * The dark color is (162, 195, 88, 255), and the light color is (188, 212, 114, 255).
     */
    @SuppressWarnings("unused")
    public static final ColorGradient GREEN = new ColorGradient(
            new Color(162, 195, 88, 255),
            new Color(188, 212, 114, 255));

    /**
     * A predefined color gradient for the DARK.
     * The dark color is (39, 36, 33, 255), and the light color is (49, 46, 43, 255).
     */
    public static final ColorGradient DARK = new ColorGradient(
            new Color(39, 36, 33, 255),
            new Color(49, 46, 43, 255));
}