package gui;

import java.awt.Color;

public class ColorGradient {
    private final Color dark;
    private final Color light;

    public ColorGradient(Color dark, Color light) {
        this.dark = dark;
        this.light = light;
    }

    public final Color getColor(boolean isGradient) {
        return isGradient ? light : dark;
    }

    public static final ColorGradient FIELD = new ColorGradient(
            new Color(122, 148, 87, 255),
            new Color(238, 238, 212, 255));
    public static final ColorGradient HIGHLIGHT = new ColorGradient(
            new Color(90, 170, 193, 255),
            new Color(114, 189, 218, 255));
    public static final ColorGradient MOVE = new ColorGradient(
            new Color(228, 167, 17, 255),
            new Color(252, 183, 43, 255));
    public static final ColorGradient ATTACK = new ColorGradient(
            new Color(212, 108, 81, 255),
            new Color(237, 126, 106, 255));
    public static final ColorGradient GREEN = new ColorGradient(
            new Color(162, 195, 88, 255),
            new Color(188, 212, 114, 255));
    public static final ColorGradient DARK = new ColorGradient(
            new Color(39, 36, 33, 255),
            new Color(49, 46, 43, 255));
}
