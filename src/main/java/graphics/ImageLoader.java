package graphics;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URL;

public class ImageLoader {
    public static BufferedImage load(String imgName) {
        try {
            URL img = ImageLoader.class.getClassLoader()
                .getResource("img/" + imgName);
            return img == null ? null : ImageIO.read(img);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private ImageLoader() {
    }
}
