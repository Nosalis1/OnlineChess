package gui;

import utility.math.Vector;

import javax.swing.*;
import java.awt.*;

@SuppressWarnings("unused")
public class Window extends JFrame {
    public static final java.awt.Color INTERFACE_COLOR = new Color(49, 46, 43, 255);

    private Vector position = new Vector(200, 200);
    private Vector dimensions = new Vector(400, 400);

    public void setPosition(Vector position) {
        this.position = position;
        updateBounds();
    }

    public void setDimensions(Vector dimensions) {
        this.dimensions = dimensions;
        updateBounds();
    }

    private void updateBounds() {
        super.setBounds(position.x, position.y, dimensions.x, dimensions.y);
    }

    public Window() {
        super();

        setTitle("New Window");

        updateBounds();
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        getContentPane().setBackground(INTERFACE_COLOR);
    }

    public Window(String title) {
        super();

        setTitle(title);

        updateBounds();
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        getContentPane().setBackground(INTERFACE_COLOR);
    }

    public Window(String title, Vector position, Vector dimensions) {
        super();

        setTitle(title);

        this.position = position;
        this.dimensions = dimensions;
        updateBounds();
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        getContentPane().setBackground(INTERFACE_COLOR);
    }

    public void showWindow() {
        super.setVisible(true);
    }

    public void hideWindow() {
        super.setVisible(false);
    }
}
