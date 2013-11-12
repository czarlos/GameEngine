package grid;

import gameObject.GameObject;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import view.Drawable;


@JsonAutoDetect
public class Tile implements Drawable {
    private boolean isActive;
    private Map<String, Double> myStatMods;
    private String myImagePath;
    private int myMoveCost;
    private String myName;
    private List<String> myPassableList;

    public Tile () {
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
        if (!isActive) {
            g.drawImage(getImage(), x, y, width, height, null);
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

    public String getImagePath () {
        return myImagePath;
    }

    public void setImagePath (String imagePath) {
        myImagePath = imagePath;
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
