package grid;

import gameObject.GameObject;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAutoDetect;


@JsonAutoDetect
public class Tile extends GameObject {
    private boolean isActive;
    private Map<String, Double> myStatMods;
    private int myMoveCost;
    private List<String> myPassableList;
    private BufferedImage myImage;

    public Tile () {
        setPassableList(new java.util.ArrayList<String>());
        setStatMods(new java.util.HashMap<String, Double>());
        setActive(false);
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

    public boolean isActive () {
        return isActive;
    }

    public void setActive (boolean active) {
        isActive = active;
        myImage = isActive ? ImageManager.getHightlightedTileImage(myImagePath)
                          : ImageManager.getTileImage(myImagePath);
    }

    public Map<String, Double> getStatMods () {
        return myStatMods;
    }

    public void setStatMods (Map<String, Double> statMods) {
        myStatMods = statMods;
    }

    public void setImagePath (String imagePath) {
        myImagePath = imagePath;
        try {
            myImage = ImageManager.addImage(imagePath);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Image getImage(){
        return myImage;
    }
    
    public int getMoveCost () {
        return myMoveCost;
    }

    public void setMoveCost (int moveCost) {
        myMoveCost = moveCost;
    }

}
