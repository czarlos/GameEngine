package grid;

import gameObject.GameObject;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import view.Drawable;


public class Tile implements Drawable {
    private boolean isActive;
    private Map<String, Double> myStatMods;
    private Image myImage;
    private int myMoveCost;
    private String myName;
    private List<String> passableList;

    public Tile () {
        isActive = false;
        myStatMods = new HashMap<String, Double>();
        try {
            myImage=ImageIO.read(new File("resources/tile.png"));
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        myMoveCost = 1;
        myName = "Grass";
        passableList = new ArrayList<String>();
    }

    public void paint (Graphics g) {
        // TODO
    }

    public boolean isPassable (GameObject unit) {
        // TODO: Want to let player pass through affiliated units
        for (String object : passableList) {
            if (object.equals(unit.getName())) { return true; }
        }

        return false;
    }

    public boolean isActive () {
        return isActive;
    }

    public void setActive (boolean active) {
        isActive = active;
    }

    public Map<String, Double> getStatMods () {
        return myStatMods;
    }

    public void setStatMods (Map<String, Double> statMods) {
        myStatMods = statMods;
    }

    public Image getImagePath () {
        return myImage;
    }

    public void setImagePath (Image image) {
        myImage = image;
    }

    public int getMoveCost () {
        return myMoveCost;
    }

    public void setMoveCost (int moveCost) {
        myMoveCost = moveCost;
    }

    public String getName () {
        return myName;
    }

    public void setName (String name) {
        myName = name;
    }

    @Override
    public void draw (Graphics g, int x, int y, int width, int height) {
        // set ImageObserver null. Not needed.
        g.drawImage(myImage, x, y, width, height, null);
    }
}
