package utility.customGui;

import gui.images.Image;

import javax.swing.*;
import java.awt.*;

/**
 * CustomImage is a customized JPanel used for displaying an image with an optional label.
 */
@SuppressWarnings("unused")
public class CustomImage extends JPanel {

    private Image image;
    protected CustomLabel imageLabel;
    private String label;
    protected Dimension dimensions;

    /**
     * Initialize the CustomImage panel with default settings.
     */
    public CustomImage() {
        this(null, null);
    }

    /**
     * Initialize the CustomImage panel with a label.
     *
     * @param label the label text to be displayed
     */
    public CustomImage(String label) {
        this(null, label);
    }

    /**
     * Initialize the CustomImage panel with an image.
     *
     * @param image the image to be displayed
     */
    public CustomImage(Image image) {
        this(image, null);
    }

    /**
     * Initialize the CustomImage panel with an image and a label.
     *
     * @param image the image to be displayed
     * @param label the label text to be displayed
     */
    public CustomImage(Image image, String label) {
        super();
        this.image = image;
        this.label = label;
        this.dimensions = new Dimension(100, 100);
        initialize();
    }

    /**
     * Set the dimensions of the CustomImage panel.
     *
     * @param width  the width of the panel
     * @param height the height of the panel
     */
    public void setDimensions(int width, int height) {
        this.dimensions.setSize(width, height);
        setPreferredSize(dimensions);
        revalidate();
    }

    /**
     * Set the color of the CustomImage panel.
     *
     * @param color the color to set
     */
    public void setColor(Color color) {
        setBackground(color);
    }

    /**
     * Set the image to be displayed.
     *
     * @param image the image to set
     */
    public void setImage(Image image) {
        this.image = image;
        repaint();
    }

    /**
     * Set the label text to be displayed.
     *
     * @param label the label text to set
     */
    public void setLabel(String label) {
        this.label = label;
        if (imageLabel != null) {
            imageLabel.setText(label);
        }
    }

    /**
     * Initialize the CustomImage panel.
     */
    private void initialize() {
        setLayout(new BorderLayout());
        createLabel();
    }

    /**
     * Create and configure the image label.
     */
    private void createLabel() {
        imageLabel = new CustomLabel(label);
        add(imageLabel, BorderLayout.CENTER);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (isDisplayable() && image != null) {
            int imageScale = 15;
            int x = imageScale / 2;
            int y = imageScale / 2;
            int width = dimensions.width - imageScale;
            int height = dimensions.height - imageScale;
            g.drawImage(image.getImage(), x, y, width, height, null);
        }
    }
}