package grid;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import utils.ImageUtilities;


public class ImageManager {

	private static int ImageOpCode;
	private static Map<String, BufferedImage> ourTileImages;
    private static Map<String, BufferedImage> ourHighlightedImage;

    static {
        ourTileImages = new HashMap<>();
        ourHighlightedImage = new HashMap<>();

    }

    public static BufferedImage getHightlightedTileImage (String filePath) {
        return ourHighlightedImage.get(filePath);
    }

    public static BufferedImage getTileImage (String filePath) {
        return ourTileImages.get(filePath);
    }

    public static BufferedImage addImage (String filePath) throws Exception {

        if (!ourTileImages.containsKey(filePath)) {
            BufferedImage img;
            try {
                img = ImageIO.read(new File(filePath));
            }
            catch (Exception e) {
                throw new Exception("Error reading image file.");
            }

            ourTileImages.put(filePath, img);
            ourHighlightedImage.put(filePath, highlightImage(img, ImageOpCode));
        }

        return ourTileImages.get(filePath);

    }

    private static BufferedImage highlightImage (BufferedImage img, int OpNumber) {
        return ImageUtilities.highlight(img,OpNumber);
    }
}
