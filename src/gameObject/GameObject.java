package gameObject;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import view.Drawable;


@JsonAutoDetect
public class GameObject implements Drawable {
    protected String myName;
    protected String myImagePath;
    protected List<String> myPassableList;

    public GameObject () {
        setPassableList(new java.util.ArrayList<String>());
    }

    public boolean isPassable (GameObject unit) {
        for (String object : myPassableList) {
            if (object.equals(unit.getName())) { return true; }
        }

        return false;
    }

    public void addPassable (String passable) {
        myPassableList.add(passable);
    }

    public void setPassableList (List<String> passables) {
        myPassableList = passables;
    }

    public List<String> getPassableList () {
        return myPassableList;
    }

    @Override
    public void draw (Graphics g, int x, int y, int width, int height) {
        // set ImageObserver null. Not needed.
        g.drawImage(getImage(), x, y, width, height, null);
    }

    public String getName () {
        return myName;
    }

    public void setName (String name) {
        myName = name;
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
