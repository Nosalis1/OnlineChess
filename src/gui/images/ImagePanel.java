package gui.images;

import util.Vector;

import javax.swing.*;
import java.awt.*;

public class ImagePanel extends JPanel {

    private Image image;

    @SuppressWarnings("unused")
    public Image getImage() {
        return this.image;
    }

    public void setImage(Image image) {
        this.image = image;
        repaint();
    }

    private JLabel jLabel = null;

    public JLabel getjLabel() {
        return this.jLabel;
    }

    public void setjLabelPosition(final int x, final int y) {
        if (jLabel == null)
            return;

        jLabel.setLocation(x, y);
    }

    public void setjLabelDimension(final int x, final int y) {
        if (jLabel == null)
            return;

        jLabel.setPreferredSize(new Dimension(x, y));
    }

    private String label;

    @SuppressWarnings("unused")
    public String getLabel() {
        return this.label;
    }

    public void setLabel(String label) {
        this.label = label;
        if (jLabel != null && this.label != null)
            jLabel.setText(this.label);
    }

    public ImagePanel() {
        super();

        this.image = null;
        this.label = null;

        initialize();
    }

    @SuppressWarnings("unused")
    public ImagePanel(String label) {
        super();

        this.image = null;
        this.label = label;

        initialize();
    }

    @SuppressWarnings("unused")
    public ImagePanel(Image image) {
        super();

        this.image = image;
        this.label = null;

        initialize();
    }

    @SuppressWarnings("unused")
    public ImagePanel(Image image, String label) {
        super();

        this.image = image;
        this.label = label;

        initialize();
    }

    private final util.Vector dimensions = new Vector(100);

    public util.Vector getVecDimensions() {
        return this.dimensions;
    }
    public void setVecDimensions(int width,int height) {
        this.dimensions.x = width;
        this.dimensions.y = height;

        setPreferredSize(new Dimension(width, height));
    }

    private void initialize() {
        setVecDimensions(100,100);

        setLayout(null);

        if (this.label == null)
            createLabel();
    }

    private void createLabel() {
        jLabel = new JLabel("");
        jLabel.setBounds(10, 10, 10, 10);

        setLabel(this.label);
        add(jLabel);
    }

    public void setColor(java.awt.Color color) {
        setBackground(color);
    }

    private int imageScale = 30;

    @SuppressWarnings("unused")
    public int getImageScale() {
        return this.imageScale;
    }

    @SuppressWarnings("unused")
    public void setImageScale(int imageScale) {
        this.imageScale = imageScale;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (isDisplayable() && this.image != null)
            g.drawImage(image.getImage(),
                    imageScale / 2, imageScale / 2,
                    dimensions.x - imageScale, dimensions.y - imageScale,
                    null);
    }
}