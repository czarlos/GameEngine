package game;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import utils.ImageUtilities;


public class ImageManager {

    private static int ImageOpCode;
    private static Map<String, BufferedImage> ourImages;
    private static Map<String, BufferedImage> ourHighlightedImage;

    static {
        ourImages = new HashMap<>();
        ourHighlightedImage = new HashMap<>();

    }

    public static BufferedImage getHightlightedTileImage (String filePath) {
        if (!ourImages.containsKey(filePath)) {
            try {
                addImage(filePath);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

        return ourHighlightedImage.get(filePath);
    }

    public static BufferedImage getImage (String filePath) {
        if (!ourImages.containsKey(filePath)) {
            try {
                addImage(filePath);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return ourImages.get(filePath);
    }

    private static BufferedImage addImage (String filePath) throws Exception {

        if (!ourImages.containsKey(filePath)) {
            BufferedImage img;
            BufferedImage highlightedImage;
            try {
                img = ImageIO.read(new File(filePath));
                highlightedImage = ImageIO.read(new File(filePath));
            }
            catch (Exception e) {
                throw new Exception("Error reading image file.");
            }

            ourImages.put(filePath, img);
            ourHighlightedImage.put(filePath, ImageUtilities.highlight(highlightedImage, ImageOpCode));
            ourImages.put(filePath, img);
        }

        return ourImages.get(filePath);

    }
}
