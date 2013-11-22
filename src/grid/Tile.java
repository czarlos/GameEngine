package grid;

import gameObject.GameObject;
import java.awt.Image;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAutoDetect;


/**
 * 
 * Tile Class. Held by grid. Affects unit stats and movement.
 * 
 * @author Kevin
 * @author Ken
 * 
 */
@JsonAutoDetect
public class Tile extends GameObject {
    private boolean isActive;
    private Map<String, Double> myStatMods;
    private int myMoveCost;

    public Tile () {
    }

    public boolean isActive () {
        return isActive;
    }

    /**
     * Sets tile to active, and changes the tile image to reflect the active state
     * 
     * @param active - boolean that is used to set
     */
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

    /**
     * Sets the image path and image for the graphic that is drawn
     * 
     * @param imagePath - String of image path
     */


    @Override
    public Image getImage () {
        return myImage;
    }

    public int getMoveCost () {
        return myMoveCost;
    }

    public void setMoveCost (int moveCost) {
        myMoveCost = moveCost;
    }

}
