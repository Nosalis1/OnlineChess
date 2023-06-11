package gui.images;

import utility.Asset;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * The Image class represents an image loaded from a file.
 */
public class Image extends Asset {

    private final BufferedImage image;

    public final BufferedImage getImage() {
        return this.image;
    }

    public Image(final String path) {
        super(new File(path).getName(), path);

        BufferedImage temp;

        File imageFile = new File(path);
        try {
            temp = ImageIO.read(imageFile);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        this.image = temp;
    }
}