package gui;

import utility.math.Vector;

import javax.swing.*;
import java.awt.*;

@SuppressWarnings("unused")
public class Window extends JFrame {
    public static final java.awt.Color INTERFACE_COLOR = new Color(49, 46, 43, 255);

    private Vector position = new Vector(200, 200);
    private Vector dimensions = new Vector(400, 400);

    /**
     * Sets the position of the window.
     *
     * @param position the position vector
     */
    public void setPosition(Vector position) {
        this.position = position;
        updateBounds();
    }

    /**
     * Sets the dimensions of the window.
     *
     * @param dimensions the dimensions vector
     */
    public void setDimensions(Vector dimensions) {
        this.dimensions = dimensions;
        updateBounds();
    }

    /**
     * Updates the window bounds based on the position and dimensions.
     */
    private void updateBounds() {
        super.setBounds(position.x, position.y, dimensions.x, dimensions.y);
    }

    /**
     * Creates a new window with default title and properties.
     */
    public Window() {
        super();

        setTitle("New Window");

        updateBounds();
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        getContentPane().setBackground(INTERFACE_COLOR);
    }

    /**
     * Creates a new window with the specified title.
     *
     * @param title the title of the window
     */
    public Window(String title) {
        super();

        setTitle(title);

        updateBounds();
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        getContentPane().setBackground(INTERFACE_COLOR);
    }

    /**
     * Creates a new window with the specified title, position, and dimensions.
     *
     * @param title      the title of the window
     * @param position   the position of the window
     * @param dimensions the dimensions of the window
     */
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

    /**
     * Makes the window visible.
     */
    public void showWindow() {
        super.setVisible(true);
    }

    /**
     * Hides the window.
     */
    public void hideWindow() {
        super.setVisible(false);
    }
}