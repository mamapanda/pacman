package graphics;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;

public class ImageLoader {
    public static final String IMG_PATH =
        new File("package.bluej").exists() ? "../img/" : "img/";

    public static BufferedImage load(String imgName) {
        try {
            return ImageIO.read(new File(IMG_PATH + imgName));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private ImageLoader() {
    }
}
