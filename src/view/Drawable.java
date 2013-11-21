package view;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


public abstract class Drawable extends Customizable {
    public void draw (Graphics g, int x, int y, int width, int height) {
        g.drawImage(getImage(), x, y, width, height, null);
    }
    
    public Image getImage () {
        try {
            return ImageIO.read(new File(myImagePath));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getImagePath () {
        return myImagePath;
    }

    public void setImagePath (String imagePath) {
        myImagePath = imagePath;
    }
}
