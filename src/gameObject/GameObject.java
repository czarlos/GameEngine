package gameObject;

import grid.GridConstants;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import view.Drawable;


public class GameObject implements Drawable {
    private String myName;
    private Image myImage;

    public GameObject () {
        myName = GridConstants.DEFAULT_OBJECT_NAME;
        setImage(GridConstants.DEFAULT_OBJECT_PATH);
    }
    
    public GameObject (String name, String imagePath) {
        myName = name;
        setImage(imagePath);
    }

    @Override
    public void draw (Graphics g, int x, int y, int width, int height) {
        // set ImageObserver null. Not needed.
        g.drawImage(myImage, x, y, width, height, null);
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

    public void setImage (String imagePath) {
        try {
            myImage=ImageIO.read(new File(imagePath));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}
