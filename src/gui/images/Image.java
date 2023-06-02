package gui.images;

import util.Console;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Image {

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
    public  static  void wakeUp() {
        return;
    }

    private BufferedImage image;

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

    public Image(String path) {
        load(path);
    }
}