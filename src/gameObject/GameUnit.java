package gameObject;

import game.ImageManager;
import gameObject.item.*;
import grid.GridConstants;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * GameUnit is any unit in the game that can be interacted with. They can move, perform actions, have stats, and hold items.
 * 
 * @author Kevin, Andy, carlosreyes
 * 
 */
@JsonAutoDetect
public class GameUnit extends InventoryObject {

    private Stats myStats;
    private String myAffiliation;
    private boolean hasMoved;

    public GameUnit () {
        myStats = new Stats();
    }

    // should ONLY be called by stage when adding units to a team
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
    public int getTotalStat (String statName) {
        int value = myStats.getStatValue(statName);
        for (Item item : myItems) {
            value += item.getStat(statName);
        }
        return value;
    }

    // Adding for Outcomes, can potentially change later
    // Need to keep method names and signatures similar for reflection
    // since dealing with different data structures
    public int combatGetStatValue (String statName) {
        return getTotalStat(statName);
    }

    public void combatSetStatValue (String statName, int statValue) {
        int baseStatDiff = getTotalStat(statName) - myStats.getStatValue(statName);
        myStats.modExisting(statName, statValue - baseStatDiff);
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
        displayData.add("<html><b>Team: </b>" + myAffiliation + "</html>");
        displayData.add("<html><b>Stats: </b></html>");
        displayData.add("    health: " + getTotalStat("health") + " / " +
                        myStats.getStatValue("maxhealth"));
        for (String stat : myStats.getStatNames()) {
            if (!stat.equals("health") && !stat.equals("maxhealth")) {
                displayData.add("    " + stat + ": " + getTotalStat(stat));
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

    public void syncActionsWithMaster (Map<String, String> nameTranslations,
                                       List<String> removedActions) {
        for (Item item : myItems) {
            for (String removedAction : removedActions) {
                if (item.getActions().contains(removedAction)) {
                    item.removeAction(removedAction);
                }
            }
            for (String action : nameTranslations.keySet()) {
                item.removeAction(action);
                item.addAction(nameTranslations.get(action));
            }
        }
    }
    
    public void syncStatsWithMaster (Map<String, String> nameTranslationMap,
                                     List<String> removedNames) {
        for (String removedStat : removedNames) {
            myStats.remove(removedStat);
            for (Item item : myItems) {
                item.removeStat(removedStat);
            }
        }

        for (String oldName : nameTranslationMap.keySet()) {
            myStats.changeName(oldName, nameTranslationMap.get(oldName));
            for (Item item : myItems) {
                item.changeStatName(oldName, nameTranslationMap.get(oldName));
            }
        }
    }

    public void setStats (Stats stats) {
        myStats = new Stats(stats);
    }

    public Stats getStats () {
        return myStats;
    }

    public int getStat (String statName) {
        return myStats.getStatValue(statName);
    }

    public void setStat (String statName, int statValue) {
        myStats.modExisting(statName, statValue);
    }
}
