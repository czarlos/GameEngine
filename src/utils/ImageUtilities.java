package utils;

import grid.GridConstants;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.*;


public class ImageUtilities {

    public enum ImageOps {
        WHITE(250, 250, 250, 0, 0),
        RED(250, 0, 0, 0, 0),
        GREEN(0, 250, 0, 0, 0),
        BLUE(0, 0, 250, 0, 0);

        private Integer R;
        private Integer G;
        private Integer B;
        private Integer REC_XCOR;
        private Integer REC_YCOR;

        ImageOps (int R, int G, int B, int REC_XCOR, int REC_YCOR) {
            this.R = R;
            this.G = G;
            this.B = B;
            this.REC_XCOR = REC_XCOR;
            this.REC_YCOR = REC_YCOR;
        }

        public int Red () {
            return R;
        }

        public int Green () {
            return G;
        }

        public int Blue () {
            return B;
        }

        public int XCor () {
            return REC_XCOR;
        }

        public int YCor () {
            return REC_YCOR;
        }
    }

    public static BufferedImage highlight (BufferedImage image, int which) {
        try {
            Graphics2D g = image.createGraphics();
            Color color =
                    new Color(ImageOps.values()[which].Red(), ImageOps.values()[which].Green(),
                              ImageOps.values()[which].Blue(),
                              255 * GridConstants.TRANSPARENCY / 100);
            g.setColor(color);
            g.fillRect(ImageOps.values()[which].XCor(), ImageOps.values()[which].YCor(),
                       image.getWidth(), image.getHeight());
        }
        catch (Exception e) {
            System.out.println("The filter is not defined");

        }
        return image;
    }
    
    public static BufferedImage getScaledImage(Image source, int width, int height){
        BufferedImage scaledImage = new BufferedImage(width, height, BufferedImage.TRANSLUCENT);
        Graphics2D graphic = scaledImage.createGraphics();
        graphic.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphic.drawImage(source, 0, 0, width, height, null);
        graphic.dispose();
        return scaledImage;
    }

}
