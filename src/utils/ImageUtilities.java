package utils;

import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;


public class ImageUtilities {

    private static final float RESIZE_FACTOR = 1.0f;
    private static final float BRIGHTNESS_OFFSET = 170.0f;

    public static BufferedImage brightenImage (BufferedImage img) {
        // RenderingHints not needed
        RescaleOp brighten = new RescaleOp(RESIZE_FACTOR, BRIGHTNESS_OFFSET, null);
        BufferedImage brightenedImg = brighten.filter(img,null);
        return brightenedImg;
    }
}
