package gameObject;

import gameObject.action.Action;
import gameObject.action.MoveAction;
import gameObject.action.WaitAction;
import gameObject.item.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * Game Unit is any unit in the game that can be interacted with,
 * game units can be stationary or can move, these units can have stats
 * and they can hold items.
 * 
 * @author carlosreyes
 * 
 */

@JsonAutoDetect
public class GameUnit extends GameObject {

    private boolean isControllable;
    @JsonProperty
    private Map<String, Integer> myItemAmounts; // might have run into problems using Items as keys.
                                                // Definitely would have run into problems with JSON
                                                // storage
    private Set<Item> myItems;
    private Stats myStats;
    private String myTeamName;
    private Weapon myActiveWeapon;
    private boolean isActive;
    private boolean hasMoved;

    // reads defaults from JSON. To add/test new defaults, edit MakeDefaults.java
    public GameUnit () {
        myItems = new HashSet<Item>();
        myItemAmounts = new HashMap<String, Integer>();
        myStats = new Stats();
        myTeamName = "";
    }

    // should ONLY be called by stage when adding units to a team
    public void setAffiliation (String affiliation) {
        myTeamName = affiliation;
    }

    public String getAffiliation () {
        return myTeamName;
    }

    /**
     * Sets the Game units active weapon to the weapon
     * with a given string name.
     * 
     * @param weaponName - The string which represents a weapon
     */
    public void selectWeapon (String weaponName) {
        for (Item item : myItems) {
            if (item.getName().equals(weaponName)) {
                myActiveWeapon = (Weapon) item;
            }
        }
    }

    /**
     * Takes an item and adds it to the list of items, adding
     * to the stats of the unit as it adds in an item.
     * 
     * @param itemName - The name of the item, not a string
     */
    public void addItem (Item item) {

        if (myItems.add(item)) {
            myItemAmounts.put(item.getName(), 1);

        }
        else {
            myItemAmounts.put(item.getName(), myItemAmounts.get(item.getName()) + 1);
        }
    }

    /**
     * Removes a particular item from the units itemList, ensures that upon removal
     * the unit's stats get decremented accordingly.
     * 
     * @param itemName - The name of the item, not a string
     */
    public void removeItem (Item itemName) {
        myItemAmounts.remove(itemName);
    }

    @Override
    public boolean isPassable (GameUnit unit) {
        unit.getAffiliation();
        return super.isPassable(unit) || unit.getAffiliation().equals(myTeamName);
    }

    /**
     * Gets the total stat value for a given stat of a character
     * after all of the item's stats have been applied. Assuming
     * initializeStats has been called.
     * 
     * @param stat - The stat that we want to see
     * @return
     */
    public int getTotalStat (String stat) {
        int value = myStats.getStatValue(stat);
        for (Item i : myItems) {
            value += i.getStat(stat);
        }
        return value;
    }

    /**
     * Trade allows one unit to swap an item with another unit, no matter
     * what team they are affiliated with. Note: as of this implementation
     * any character will trade with you for anything you want, a system must
     * be implemented which allows the other unit to determine what trades are
     * appropriate.
     * 
     * @param other - The unit that this unit is trading with
     * @param otherItem - The item that this unit wants
     * @param item - The item that this unit is giving away
     */
    public void trade (GameUnit other, Item otherItem, Item item) {
        other.removeItem(otherItem);
        this.removeItem(item);
        other.addItem(item);
        this.addItem(otherItem);
    }

    public void setStats (Stats stats) {
        myStats = new Stats(stats);
    }

    public Stats getStats () {
        return myStats;
    }

    // Adding for Outcomes, can potentially change later
    // Need to keep method names and signatures similar for reflection
    // since dealing with different data structures
    public int getStat (String statName) {
        return myStats.getStatValue(statName);
    }

    public void setStat (String statName, int statValue) {
        myStats.modExisting(statName, statValue);
    }

    public boolean isControllable () {
        return isControllable;
    }

    public void setControllable (boolean controllable) {
        isControllable = controllable;
    }

    public Weapon getActiveWeapon () {
        return myActiveWeapon;
    }

    public void setActiveWeapon (Item activeItem) {
        myActiveWeapon = (Weapon) activeItem;
    }

    public void setActive (boolean active) {
        hasMoved = active;
        isActive = active;
    }

    public boolean getActiveStatus () {
        return isActive;
    }

    @JsonIgnore
    public List<Action> getActions () {
        List<Action> actions = new ArrayList<>();
        if(isActive){
            if (!hasMoved) {
                actions.add(new MoveAction());
            }
                actions.add(new WaitAction());
            for (Item item : myItems) {
                actions.addAll(item.getActions());
            }
        }
        return actions;
    }

    /**
     * Generates the List of Strings that the unit will display to the user
     */
    public void generateDisplayData () {
        List<String> displayData = new ArrayList<>();
        displayData.add("Name: " + myName);
        displayData.add("Team: " + myTeamName);
        displayData.add("");
       // displayData.add("Equipped Item: " + myActiveWeapon.getName());
        displayData.add("");
        displayData.add("Stats: ");
        displayData
                .add("Health: " + getTotalStat(GameObjectConstants.HEALTH) + " / " +
                     myStats.getStatValue("maxhealth"));

        for (String stat : myStats.getStatNames()) {
            if (stat.equals(GameObjectConstants.HEALTH)) {
                continue;
            }
            else {
                displayData.add(stat + ": " + getTotalStat(stat));
            }
        }
        setDisplayData(displayData);
    }

    // TODO: trade with affiliates
    @Override
    public Action getInteraction () {
        return null;
    };

    public void hasMoved () {
        hasMoved = true;
    }

    @JsonIgnore
    public int getItemAmount (String itemName) {
        for (Item i : myItems) {
            if (i.getName().equals(itemName)) { return myItemAmounts.get(i); }
        }
        return 0;
    }

    public void setItems (Set<Item> itemList) {
        myItems = itemList;
    }

    public Set<Item> getItems () {
        return myItems;
    }

}
