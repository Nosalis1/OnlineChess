package gui.design;

import util.ColorGradient;

import javax.swing.*;

public class Label extends JLabel {

    private void initialize() {
        super.setForeground(ColorGradient.GREEN.getColor(false));
        super.setFont(Fonts.LABEL_FONT);
    }

    public Label() {
        super();
        initialize();
    }

    public Label(String text) {
        super(text);
        initialize();
    }

    public Label(String text, int x, int y) {
        super(text);
        super.setBounds(x, y, 120, 20);
        initialize();
    }

    public Label(String text, int x, int y, int width, int height) {
        super(text);
        super.setBounds(x, y, width, height);
        initialize();
    }
}
