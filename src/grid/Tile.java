package grid;

import gameObject.GameObject;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

    private Map<String, Double> myStatMods;
    private int myMoveCost;
    private List<Tile> myNeighbors;
    private Tile myParent;
    private int myLength;
    private int myDistanceToGoal;
    private Coordinate myCoordinate;

    public Tile () {
        setImagePath(myImagePath);
        List<String> displayData = new ArrayList<>();
        displayData.add("Name: " + myName);
        displayData.add("Movement cost: " + myMoveCost);
        displayData.add("Stat Modifiers: ");
        for (String stat : myStatMods.keySet()) {
            displayData.add(stat + ": " + myStatMods.get(stat));
        }
        myDisplayData = displayData;
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

    public boolean isActive(){
        return isActive;
    }
    
    public void setActive(boolean active){
        isActive=active;
    }

    public int getMoveCost () {
        return myMoveCost;
    }

    public void setMoveCost (int moveCost) {
        myMoveCost = moveCost;
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
