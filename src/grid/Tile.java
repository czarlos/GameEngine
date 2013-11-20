package grid;

import gameObject.GameObject;
import java.awt.Image;
<<<<<<< HEAD
=======
import java.awt.image.BufferedImage;
import java.util.List;
>>>>>>> Added doAIMove to stage, added the functionality of node to tile
import java.util.Map;
import unit_ai.Node;
import com.fasterxml.jackson.annotation.JsonAutoDetect;


/**
 * 
 * Tile Class. Held by grid. Affects unit stats and movement.
 * 
 * @author Kevin
 * @author Ken
 * @author carlosreyes
 * 
 */
@JsonAutoDetect
public class Tile extends GameObject {
    private boolean isActive;
    private Map<String, Double> myStatMods;
    private int myMoveCost;
    private BufferedImage myImage;
    
    //Moved from node class
    private List<Tile> myNeighbors;
    private Tile myParent;
    private int myLength;
    private int myDistanceToGoal;
    private Coordinate myCoordinate;
    
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
    
    /*
     * Moved from node class
     */
    
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
