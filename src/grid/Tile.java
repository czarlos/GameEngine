package grid;

import gameObject.GameObject;
import gameObject.IStats;
import gameObject.Stat;
import gameObject.Stats;
import java.util.List;
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
public class Tile extends GameObject implements IStats {
    private Stats myStats;
    private int myMoveCost;
    private List<Tile> myNeighbors;
    private Tile myParent;

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
        displayData.add("<b>Movement cost: </b>" + myMoveCost);
        displayData.add("<b>Stat Modifiers: </b>");
        for (String stat : myStats.getStatNames()) {
            if (!stat.equals("health") && !stat.equals("maxhealth") && !stat.equals("experience")) {
                displayData.add("&nbsp; &nbsp; &nbsp;" + stat + ": " + myStats.getStatValue(stat));
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

    public void removeStat (String removedStat) {
        myStats.remove(removedStat);
    }

    public void changeStatName (String oldName, String newName) {
        myStats.changeName(oldName, newName);
    }

    public boolean containsStat (String name) {
        return myStats.contains(name);
    }

    public void addStat (Stat stat) {
        myStats.addStat(stat);
    }
}
