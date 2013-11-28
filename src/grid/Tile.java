package grid;

import gameObject.GameObject;
import gameObject.Stats;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
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
    private Stats myStats;
    private int myMoveCost;
    private List<Tile> myNeighbors;
    private Tile myParent;
    private int myLength;
    private int myDistanceToGoal;
    private Coordinate myCoordinate;

    public Tile () {
    }

    public void setActive (boolean active) {
        isActive = active;
        myImage = isActive ? ImageManager.getHightlightedTileImage(myImagePath)
                          : ImageManager.getTileImage(myImagePath);
    }

    public Stats getStats () {
        return myStats;
    }

    public void setStats (Stats stat) {
        myStats = new Stats(stat);
    }

    /**
     * Sets the image path and image for the graphic that is drawn
     * 
     * @param imagePath - String of image path
     */

    public boolean isActive () {
        return isActive;
    }

    public int getMoveCost () {
        return myMoveCost;
    }

    public void setMoveCost (int moveCost) {
        myMoveCost = moveCost;
    }

    public void generateDisplayData () {
        List<String> displayData = new ArrayList<>();
        displayData.add("Name: " + myName);
        displayData.add("Movement cost: " + myMoveCost);
        displayData.add("Stat Modifiers: ");
        for (String stat : myStats.getStatNames()) {
            displayData.add(stat + ": " + myStats.getStatValue(stat));
        }
        myDisplayData = displayData;
    }

    public Tile (List<Tile> neighbors, Coordinate coordinate) {
        myNeighbors = neighbors;
        myCoordinate = coordinate;
    }

    public List<Tile> getNeighbors () {
        return myNeighbors;
    }

    public void setNeighbors (List<Tile> myNeighbors) {
        myNeighbors = myNeighbors;
    }

    public Tile getParent () {
        return myParent;
    }

    public void setParent (Tile myParent) {
        myParent = myParent;
    }

    public int getLength () {
        return myLength;
    }

    public void setLength (int myLength) {
        myLength = myLength;
    }

    public int getDistanceToGoal () {
        return myDistanceToGoal;
    }

    public void setDistanceToGoal (int myDistanceToGoal) {
        myDistanceToGoal = myDistanceToGoal;
    }

    public Coordinate getCoordinate () {
        return myCoordinate;
    }

    public void setCoordinate (Coordinate myCoordinate) {
        myCoordinate = myCoordinate;
    }
}
