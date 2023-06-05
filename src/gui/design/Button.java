package gui.design;

import util.ColorGradient;

import javax.swing.*;
import java.awt.event.ActionListener;

public class Button extends JButton {

    private void initialize() {
        super.setForeground(ColorGradient.GREEN.getColor(false));
        super.setBackground(ColorGradient.DARK.getColor(false));
        super.setFont(Fonts.BUTTON_FONT);
    }

    public Button() {
        super();
        initialize();
    }

    public Button(String text) {
        super(text);
        initialize();
    }

    public Button(String text, int x, int y) {
        super(text);
        super.setBounds(x, y, 120, 20);
        initialize();
    }

    public Button(String text, int x, int y, int width, int height) {
        super(text);
        super.setBounds(x, y, width, height);
        initialize();
    }

    public Button(ActionListener l) {
        super();
        super.addActionListener(l);
        initialize();
    }

    public Button(String text, ActionListener l) {
        super(text);
        super.addActionListener(l);
        initialize();
    }

    public Button(String text, int x, int y, ActionListener l) {
        super(text);
        super.setBounds(x, y, 120, 20);
        super.addActionListener(l);
        initialize();
    }

    public Button(String text, int x, int y, int width, int height, ActionListener l) {
        super(text);
        super.setBounds(x, y, width, height);
        super.addActionListener(l);
        initialize();
    }
}