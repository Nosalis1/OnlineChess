package gui.images;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * The Image class represents an image loaded from a file.
 */
public class Image {

    /**
     * A two-dimensional array of Image objects representing different chess piece images.
     */
    public static final Image[][] IMAGES = {
            {
                    new Image("src/gui/images/256px/w_rook_png_shadow_256px.png"),
                    new Image("src/gui/images/256px/w_knight_png_shadow_256px.png"),
                    new Image("src/gui/images/256px/w_bishop_png_shadow_256px.png"),
                    new Image("src/gui/images/256px/w_queen_png_shadow_256px.png"),
                    new Image("src/gui/images/256px/w_king_png_shadow_256px.png"),
                    new Image("src/gui/images/256px/w_pawn_png_shadow_256px.png")
            },
            {
                    new Image("src/gui/images/256px/b_rook_png_shadow_256px.png"),
                    new Image("src/gui/images/256px/b_knight_png_shadow_256px.png"),
                    new Image("src/gui/images/256px/b_bishop_png_shadow_256px.png"),
                    new Image("src/gui/images/256px/b_queen_png_shadow_256px.png"),
                    new Image("src/gui/images/256px/b_king_png_shadow_256px.png"),
                    new Image("src/gui/images/256px/b_pawn_png_shadow_256px.png")
            }
    };

    /**
     * Initializes the Image class.
     * This method can be used to perform any necessary setup or initialization tasks.
     */
    public static void wakeUp() {
    }

    private BufferedImage image;

    /**
     * Gets the BufferedImage object representing the image.
     *
     * @return the BufferedImage object
     */
    public BufferedImage getImage() {
        return this.image;
    }

    private void load(final String path) {
        util.Console.message("Loading new Image : " + path, this);

        File file = new File(path);
        try {
            this.image = ImageIO.read(file);
        } catch (IOException ex) {
            util.Console.error("Failed to load new Image : " + path, this);
            ex.printStackTrace();
        }
        util.Console.message("Image loaded : " + file.getName(), this);
    }

    /**
     * Creates a new instance of the Image class with the specified image file path.
     *
     * @param path the path to the image file
     */
    public Image(String path) {
        load(path);
    }
}