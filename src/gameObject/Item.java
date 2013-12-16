package gameObject;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * Items have a name, items can have a wide range of effects including effecting
 * stats, which is evident in the statEffect abstract method. Alternatively they
 * can have an effect on the properties of a gameunit, such as reviving a units
 * health.
 * 
 * @author carlosreyes, Ken
 * 
 */
@JsonAutoDetect
public class Item extends Customizable implements IStats {
    private List<String> myActions;
    private Stats myStats;

    public Item () {
        myActions = new ArrayList<>();
        myStats = new Stats();
    }

    /**
     * @return The item's list of actions
     */
    public List<String> getActions () {
        return myActions;
    }

    /**
     * Sets the item's list of actions
     * 
     * @param newActions New list of actions to set to
     */
    public void setActions (List<String> newActions) {
        myActions = newActions;
    }

    @JsonIgnore
    @Deprecated
    public void addAction (String action) {
        myActions.add(action);
    }

    /**
     * Removes an action from the item's action list
     * 
     * @param action Name of the action to removed from the item's action list
     */
    public void removeAction (String action) {
        int removeIndex = myActions.indexOf(action);
        if (removeIndex > -1) {
            myActions.remove(removeIndex);
        }
    }

    /**
     * Adds a stat to the item's Stats instance
     * 
     * @param newStat The new stat to add to the item's Stats instance
     */
    public void addStat (Stat newStat) {
        myStats.addStat(newStat);
    }

    /**
     * Removes a stat from the item's Stats instance
     * 
     * @param removeStat The name of the stat to remove from the item's Stats instance
     */
    public void removeStat (String removeStat) {
        myStats.remove(removeStat);
    }

    /**
     * Changes the name of a stat in the item's Stats instance
     * 
     * @param oldName The original name of the stat to change
     * @param newName the new name for the stat
     */
    public void changeStatName (String oldName, String newName) {
        myStats.changeName(oldName, newName);
    }

    /**
     * Gets the value of a stat in the item's Stats instance
     * 
     * @param statName The name of the stat to get the value of
     * @return The value of the given stat name
     */
    @JsonIgnore
    public int getStat (String statName) {
        return myStats.getStatValue(statName);
    }

    /**
     * @return The item's Stats instance
     */
    public Stats getStats () {
        return myStats;
    }

    /**
     * Sets the item's Stats instance
     * 
     * @param myStats The Stats instance to set the item's Stats instance to
     */
    public void setStats (Stats myStats) {
        myStats = new Stats(myStats);
    }

    /**
     * Checks whether the item has the given stat
     * 
     * @param name The name of the stat to check the existence of
     * @return Whether or not the item's Stats instance contains the given stat
     */
    public boolean containsStat (String name) {
        return myStats.contains(name);
    }

    /**
     * Changes the action name of a given action in the item's action list
     * 
     * @param oldName The original name of the action to change
     * @param newName The new name for the action
     */
    public void changeActionName (String oldName, String newName) {
        if (myActions.remove(oldName)) {
            myActions.add(newName);
        }
    }
}
