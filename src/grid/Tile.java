package grid;

import gameObject.GameObject;
import gameObject.Stats;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonAutoDetect;


/**
 * 
 * Tile Class. Held by grid. Affects unit stats and movement.
 * 
 * @author Kevin, Ken, Carlos
 * 
 */
@JsonAutoDetect
public class Tile extends GameObject {
    private Stats myStats;
    private int myMoveCost;
    private List<Tile> myNeighbors;
    private Tile myParent;
    private int myLength;
    private int myDistanceToGoal;
    private Coordinate myCoordinate;

    public Tile () {
    }

    public Stats getStats () {
        return myStats;
    }

    public void setStats (Stats stat) {
        myStats = new Stats(stat);
    }

    public int getMoveCost () {
        return myMoveCost;
    }

    public void setMoveCost (int moveCost) {
        myMoveCost = moveCost;
    }

    @Override
    public List<String> generateDisplayData () {
        List<String> displayData = super.generateDisplayData();
        displayData.add("Movement cost: " + myMoveCost);
        displayData.add("Stat Modifiers: ");
        for (String stat : myStats.getStatNames()) {
            if (!stat.equals("health") && !stat.equals("maxhealth") && !stat.equals("experience")) {
                displayData.add("    " + stat + ": " + myStats.getStatValue(stat));
            }
        }
        setDisplayData(displayData);
        return displayData;
    }

    public Tile (List<Tile> neighbors, Coordinate coordinate) {
        myNeighbors = neighbors;
        myCoordinate = coordinate;
    }

    public List<Tile> getNeighbors () {
        return myNeighbors;
    }

    public void setNeighbors (List<Tile> neighbors) {
        myNeighbors = neighbors;
    }

    public Tile getParent () {
        return myParent;
    }

    public void setParent (Tile parent) {
        myParent = parent;
    }

    public int getLength () {
        return myLength;
    }

    public void setLength (int length) {
        myLength = length;
    }

    public int getDistanceToGoal () {
        return myDistanceToGoal;
    }

    public void setDistanceToGoal (int distanceToGoal) {
        myDistanceToGoal = distanceToGoal;
    }

    public Coordinate getCoordinate () {
        return myCoordinate;
    }

    public void setCoordinate (Coordinate coordinate) {
        myCoordinate = coordinate;
    }
}
