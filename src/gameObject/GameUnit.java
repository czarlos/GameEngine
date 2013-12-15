package gameObject;

import game.ImageManager;
import grid.GridConstants;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * GameUnit is any unit in the game that can be interacted with. They can move, perform actions,
 * have stats, and hold items.
 * 
 * @author Kevin, Andy, carlosreyes
 * 
 */
@JsonAutoDetect
public class GameUnit extends InventoryObject implements IStats {
    private Stats myStats;
    private Stats myTotalStats;
    private String myAffiliation;
    @JsonProperty
    private boolean hasMoved;

    public GameUnit () {
        myStats = new Stats();
        myTotalStats = new Stats();
    }

    public void setAffiliation (String affiliation) {
        myAffiliation = affiliation;
    }

    public String getAffiliation () {
        return myAffiliation;
    }

    @Override
    public boolean isPassable (GameUnit unit) {
        return unit.getAffiliation().equals(myAffiliation);
    }

    /**
     * Gets the total stat value for a given stat of a character after all of
     * the items' stats have been applied.
     * 
     * @param statName String of stat name being queried
     * @return int of stat value
     */
    public int calcTotalStat (String statName) {
        int value = myStats.getStatValue(statName);
        for (Item item : myItems) {
            value += item.getStat(statName);
        }
        return value;
    }

    /**
     * Adds the Stats set input with the unit stats and sets it to Total Stats
     * 
     * @param addStats Stats to add
     */
    public void setTotalStats (Stats addStats) {
        for (Stat stat : addStats.getStats()) {
            myTotalStats.modExisting(stat.getName(),
                                     calcTotalStat(stat.getName()) + stat.getValue());
        }
    }

    public int getTotalStat (String statName) {
        return myTotalStats.getStatValue(statName);
    }

    // Adding for Outcomes, can potentially change later
    // Need to keep method names and signatures similar for reflection
    // since dealing with different data structures
    public int combatGetStatValue (String statName) {
        return getTotalStat(statName);
    }

    public void combatSetStatValue (String statName, int statValue) {
        int baseStatDiff = myTotalStats.getStatValue(statName) - myStats.getStatValue(statName);
        myStats.modExisting(statName, statValue - baseStatDiff);
        myTotalStats.modExisting(statName, statValue);
    }

    public int combatGetItemValue (Item item) {
        return (myItemAmounts.containsKey(item.getName()) == false ? 0 : myItemAmounts.get(item
                .getName()));
    }

    public void combatSetItemValue (Item item, int itemValue) {
        if (myItems.add(item)) {
            myItemAmounts.put(item.getName(), itemValue);
        }
        else {
            myItemAmounts.put(item.getName(), itemValue);
        }
    }

    @Override
    public void setActive (boolean active) {
        hasMoved = !active;
        isActive = active;
        myImage = isActive ? ImageManager.getHightlightedTileImage(myImagePath)
                          : ImageManager.getImage(myImagePath);
    }

    public void hasMoved () {
        hasMoved = true;
    }

    /**
     * Gets the list of actions that the GameUnit can perform
     * 
     * @return List of Strings
     */
    @JsonIgnore
    public List<String> getActionNames () {
        List<String> actions = new ArrayList<>();
        if (isActive) {
            if (!hasMoved) {
                actions.add(GridConstants.MOVE);
            }
            actions.add(GridConstants.WAIT);
            for (Item item : myItems) {
                actions.addAll(item.getActions());
            }
        }
        return actions;
    }

    @Override
    public List<String> generateDisplayData () {
        List<String> displayData = super.generateDisplayData();
        displayData.add("<b>Team: </b>" + myAffiliation);
        displayData.add("<b>Stats: </b>");
        displayData.add("&nbsp; &nbsp; &nbsp; health: " + getTotalStat("health") + "/" +
                        getTotalStat("maxhealth"));
        for (String stat : myTotalStats.getStatNames()) {
            if (!stat.equals("health") && !stat.equals("maxhealth")) {
                displayData.add("&nbsp; &nbsp; &nbsp;" + stat + ": " + getTotalStat(stat));
            }
        }
        setDisplayData(displayData);
        return displayData;
    }

    @Override
    @JsonIgnore
    public List<String> getInteractions () {
        if (myItems.isEmpty()) { return null; }
        List<String> interactions = new ArrayList<>();
        for (Item item : myItems) {
            interactions.add(GridConstants.TRADE + " " + item.getName());
        }

        return interactions;
    };

    public void removeStat (String stat) {
        myStats.remove(stat);
        myTotalStats.remove(stat);
    }

    public void changeStatName (String oldName, String newName) {
        myStats.changeName(oldName, newName);
        myTotalStats.changeName(oldName, newName);
    }

    public Stats getStats () {
        return myStats;
    }

    public int getStat (String statName) {
        return myStats.getStatValue(statName);
    }

    public void setStats (Stats stats) {
        stats.modExisting("maxhealth", stats.getStatValue("health"));
        myStats = new Stats(stats);
        myTotalStats = new Stats(stats);
    }

    public boolean containsStat (String name) {
        return myStats.contains(name);
    }

    public void addStat (Stat stat) {
        myStats.addStat(stat);
        myTotalStats.addStat(stat);
    }
}
