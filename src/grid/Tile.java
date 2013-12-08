package grid;

import gameObject.GameObject;
import gameObject.Stats;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;


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
        displayData.add("<html><b>Movement cost: </b>" + myMoveCost+"</html>");
        displayData.add("<html><b>Stat Modifiers: </b></html>");
        for (String stat : myStats.getStatNames()) {
            if (!stat.equals("health") && !stat.equals("maxhealth") && !stat.equals("experience")) {
                displayData.add("    " + stat + ": " + myStats.getStatValue(stat));
            }
        }
        setDisplayData(displayData);
        return displayData;
    }

    @JsonIgnore
    public List<Tile> getNeighbors () {
        return myNeighbors;
    }

    @JsonIgnore
    public void setNeighbors (List<Tile> neighbors) {
        myNeighbors = neighbors;
    }

    @JsonIgnore
    public Tile getParent () {
        return myParent;
    }

    @JsonIgnore
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

    public void syncStatsWithMaster (Map<String, String> nameTranslationMap,
                                     List<String> removedNames) {
        // TODO Auto-generated method stub
        
    }
}
