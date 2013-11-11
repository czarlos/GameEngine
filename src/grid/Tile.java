package grid;

import java.awt.Color;
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
    private List<String> myPassableList;

    public Tile () {
        // TODO: Put this on JSON, use data file for defaults
        isActive = false;
        myName = GridConstants.DEFAULT_TILE_NAME;
        setImage(GridConstants.DEFAULT_TILE_PATH);
        myMoveCost = 1;
        myStatMods = new HashMap<String, Double>();
        myPassableList = new ArrayList<String>();
    }
    
    public Tile (String name, String imagePath, int moveCost, ArrayList<String> passableList, HashMap<String, Double> statMods) {
        isActive = false;
        myName = name;
        setImage(imagePath);
        myMoveCost = moveCost;
        myStatMods = statMods;
        myPassableList = passableList;
    }

    public boolean isPassable (GameObject unit) {
        for (String object : myPassableList) {
            if (object.equals(unit.getName())) { return true; }
        }

        return false;
    }

    @Override
    public void draw (Graphics g, int x, int y, int width, int height) {
        // set ImageObserver null. Not needed.
        if (!isActive) { 
            g.drawImage(myImage, x, y, width, height, null);
        }
        else {
            // TODO: make image highlighted if active
        }
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
    
}
