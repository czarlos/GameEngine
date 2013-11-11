package grid;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import view.Drawable;


public class GameObject implements Drawable {
    private String myName;
    private Image myImage;

    public GameObject (String name) {
        myName = name;
        try {
            myImage=ImageIO.read(new File("resources/tile.png"));
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public String getName () {
        return myName;
    }

    public void setName (String name) {
        myName = name;
    }

    public Image getImage () {
        return myImage;
    }

    public void setImage (Image image) {
        myImage = image;
    }
    
    @Override
    public void draw (Graphics g, int x, int y, int width, int height) {
        // set ImageObserver null. Not needed.
        g.drawImage(myImage, x, y, width, height, null);
    }
}
