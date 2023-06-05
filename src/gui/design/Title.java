package gui.design;

import util.ColorGradient;

import javax.swing.*;

public class Title extends JLabel {

    private void initialize() {
        super.setForeground(ColorGradient.GREEN.getColor(false));
        super.setHorizontalAlignment(SwingConstants.CENTER);
        super.setFont(Fonts.TITLE_FONT);
    }

    public Title() {
        super();
        initialize();
    }

    public Title(String text) {
        super(text);
        initialize();
    }

    public Title(String text, int x, int y) {
        super(text);
        super.setBounds(x, y, 120, 20);
        initialize();
    }

    public Title(String text, int x, int y, int width, int height) {
        super(text);
        super.setBounds(x, y, width, height);
        initialize();
    }
}
