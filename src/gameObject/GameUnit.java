package gameObject;

import gameObject.action.Action;
import gameObject.action.CombatAction;
import gameObject.action.MoveAction;
import gameObject.action.WaitAction;
import gameObject.item.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;


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
    private Map<Item, Integer> myItems;
    private Stats myStats;
    private String myTeamName;
    private Weapon myActiveWeapon;
    private double myMaxHealth;
    private double myExperience;
    private boolean isActive;
    private boolean hasMoved;

    // TODO: this is in make defaults. doesn't have to be here then?
    // reads defaults from JSON. To add/test new defaults, edit MakeDefaults.java
    public GameUnit () {
        myItems = new HashMap<Item, Integer>();
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
     * Loops through all of a players items and ups their stats
     * according to the stat value of each item if the player has
     * a stat field in line with that item.
     */
    public void initializeStats () {
        for (Item item : myItems.keySet()) {
            for (String statName : item.getStats().getStatMap().keySet()) {
                if (myStats.getStatNames().contains(statName)) {
                    int fromItem = item.getStats().getStatValue(statName);
                    int current = myStats.getStatValue(statName);
                    myStats.modExisting(statName, current+fromItem);
                }
            }
        }
    }

    /**
     * Sets the Game units active weapon to the weapon
     * with a given string name.
     * 
     * @param weaponName - The string which represents a weapon
     */
    public void selectWeapon (String weaponName) {
        for (Item item : myItems.keySet()) {
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
    public void addItem (Item itemName) {
        if (!myItems.containsKey(itemName)) {
            myItems.put(itemName, 1);
        }
        else {
            myItems.put(itemName, myItems.get(itemName)+1);
        }
    }

    /**
     * Removes a particular item from the units itemList, ensures that upon removal
     * the unit's stats get decremented accordingly.
     * 
     * @param itemName - The name of the item, not a string
     */
    public void removeItem (Item itemName) {
        myItems.remove(itemName);
    }

    @Override
    public boolean isPassable (GameUnit unit) {
        unit.getAffiliation();
        System.out.println(myTeamName);
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
        for (Item i : myItems.keySet()){
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
        myStats = stats;
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

    public void setControllable (boolean myControllable) {
        this.isControllable = myControllable;
    }

    public Weapon getActiveWeapon () {
        return myActiveWeapon;
    }

    public void setActiveWeapon (Item activeItem) {
        this.myActiveWeapon = (Weapon) activeItem;
    }

    public void setActive (boolean active) {
        hasMoved = active;
        isActive = active;
    }

    public boolean getActiveStatus () {
        return isActive;
    }

    public double getHealth () {
        return myMaxHealth;
    }

    public void setHealth (double health) {
        myMaxHealth = health;
    }

    public double getExperience () {
        return myExperience;
    }

    public void setExperience (double myExperience) {
        this.myExperience = myExperience;
    }

    public Map<Item, Integer> getItemMap () {
        return myItems;
    }

    // TODO: get rid of this? unit shouldn't know about defenders. that's grid.
    public List<Action> getValidActions (GameUnit defender) {
        List<Action> validActions = new ArrayList<>();
        for (Item i : myItems.keySet()) {
            if (i instanceof Weapon) {
                List<Action> tempActions = ((Weapon) i).getActions();
                for (Action ca : tempActions) {
                    if (ca.isValidAction(this, defender)) {
                        validActions.add(ca);
                    }
                }
            }
        }
        return validActions;
    }

    @JsonIgnore
    public List<Action> getActions () {
        // TODO: add in hasMoved logic/add move and wait actions
        List<Action> actions = new ArrayList<>();
        for (Item item : myItems.keySet()) {
            actions.addAll(item.getActions());
        }
        return actions;
    }

    /**
     * Generates the List of Strings that the unit will display to the user
     */
    public void generateDisplayData () {
        List<String> displayData = new ArrayList<>();
        displayData.add("Name: " + myName);
        displayData.add("Affiliation: " + myTeamName);
        displayData.add("");
        displayData.add("Equipped Item: " + myActiveWeapon.getName());
        displayData.add("");
        displayData.add("Stats: ");
        displayData.add("Health: " + getTotalStat(GameObjectConstants.HEALTH) + " / " + myMaxHealth);
        for (String stat : myStats) { // TODO: FIX
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
        this.hasMoved = true;
    }
    
    public int getItemAmount (String itemName) {
        for (Item i : myItems.keySet()) {
            if (i.getName().equals(itemName)) { return myItems.get(i); }
        }
        return 0;
    }

    public void setItemList (Map<Item, Integer> itemList) {
        myItems = itemList;
    }

}
