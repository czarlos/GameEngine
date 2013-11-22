package grid;

import gameObject.GameObject;
import java.awt.Image;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAutoDetect;


/**
 * 
 * Tile Class. Held by grid. Affects unit stats and movement.
 * 
 * @author Kevin, Ken
 * 
 */
@JsonAutoDetect
public class Tile extends GameObject {
    private boolean isActive;
    private Map<String, Double> myStatMods;
    private int myMoveCost;
    private List<Tile> myNeighbors;
    private Tile myParent;
    private int myLength;
    private int myDistanceToGoal;
    private Coordinate myCoordinate;

    public Tile () {
        setImagePath(myImagePath);
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
    public Image getImage () {
        return myImage;
    }

    public int getMoveCost () {
        return myMoveCost;
    }

    public void setMoveCost (int moveCost) {
        myMoveCost = moveCost;
    }

    public Tile (List<Tile> neighbors, Coordinate coordinate) {
        this.myNeighbors = neighbors;
        this.myCoordinate = coordinate;
    }

    public List<Tile> getNeighbors () {
        return myNeighbors;
    }

    public void setNeighbors (List<Tile> myNeighbors) {
        this.myNeighbors = myNeighbors;
    }

    public Tile getParent () {
        return myParent;
    }

    public void setParent (Tile myParent) {
        this.myParent = myParent;
    }

    public int getLength () {
        return myLength;
    }

    public void setLength (int myLength) {
        this.myLength = myLength;
    }

    public int getDistanceToGoal () {
        return myDistanceToGoal;
    }

    public void setDistanceToGoal (int myDistanceToGoal) {
        this.myDistanceToGoal = myDistanceToGoal;
    }

    public Coordinate getCoordinate () {
        return myCoordinate;
    }

    public void setCoordinate (Coordinate myCoordinate) {
        this.myCoordinate = myCoordinate;
    }
}
