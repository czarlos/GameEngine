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
    private Map<Item, Integer> myItemMap;
    private List<Action> myActionList;
    private Stat myUnitStats;
    private String myTeamName;
    private Weapon myActiveWeapon;
    private double myHealth;
    private double myExperience;
    private boolean isActive;
    private boolean hasMoved;

    // reads defaults from JSON. To add/test new defaults, edit MakeDefaults.java
    public GameUnit () {
        myItemMap = new HashMap<Item, Integer>();
        myUnitStats = new Stat();
        myTeamName = "";
    }

    // should ONLY be called by stage when adding units to a team
    public void setAffiliation (String affiliation) {
        myTeamName = affiliation;
    }

    public String getAffiliation () {
        return myTeamName;
    }

    public List<Action> getActionList() {
        List<Action> actionListOther = new ArrayList<Action>();
        if (!hasMoved) {
            for(Action a : myActionList) {
                actionListOther.add(a);
            }
            actionListOther.add(new MoveAction());
            actionListOther.add(new WaitAction());
            return actionListOther;
        }
        return myActionList;
    }
    
    
    /**
     * Loops through all of a players items and ups their stats
     * according to the stat value of each item if the player has
     * a stat field in line with that item.
     */
    public void initializeStats () {
        for (Item item : myItemMap.keySet()) {
            for (String statName : item.getStats().getStatMap().keySet()) {
                if (this.myUnitStats.getStatMap().containsKey(statName)) {
                    int fromItem = item.getStats().getStatValue(statName);
                    int current = this.myUnitStats.getStatValue(statName);
                    this.myUnitStats.getStatMap().put(statName, current+fromItem);
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
        for (Item item : myItemMap.keySet()) {
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
        for (String stat : itemName.getStats().getStatMap().keySet()) {
            if (this.myUnitStats.getStatMap().keySet().contains(stat)) {
                int fromItem = itemName.getStats().getStatValue(stat);
                int current = this.myUnitStats.getStatValue(stat);
                this.myUnitStats.getStatMap().put(stat, current+fromItem);
            }
        }
        
        if (!myItemMap.containsKey(itemName)) {
            myItemMap.put(itemName, 1);
        }
        else {
            myItemMap.put(itemName, myItemMap.get(itemName)+1);
        }
    }

    /**
     * Removes a particular item from the units itemList, ensures that upon removal
     * the unit's stats get decremented accordingly.
     * 
     * @param itemName - The name of the item, not a string
     */
    public void removeItem (Item itemName) {
        for (String stat : itemName.getStats().getStatMap().keySet()) {
            if (this.myUnitStats.getStatMap().keySet().contains(stat)) {
                int fromItem = itemName.getStats().getStatValue(stat);
                int current = this.myUnitStats.getStatValue(stat);
                this.myUnitStats.getStatMap().put(stat, current-fromItem);
            }
        }
        myItemMap.remove(itemName);
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
        return this.myUnitStats.getStatValue(stat);
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

    public void setUnitStats (Stat myUnitStats) {
        this.myUnitStats = myUnitStats;
    }

    public Stat getUnitStats () {
        return myUnitStats;
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
        this.hasMoved = active;
        this.isActive = active;
    }

    public boolean getActiveStatus () {
        return this.isActive;
    }

    public double getHealth () {
        return myHealth;
    }

    public void setHealth (double myHealth) {
        this.myHealth = myHealth;
    }

    public double getExperience () {
        return myExperience;
    }

    public void setExperience (double myExperience) {
        this.myExperience = myExperience;
    }

    public Map<Item, Integer> getItemMap () {
        return myItemMap;
    }

    @JsonIgnore
    public List<Action> getActions () {
        List<Action> actions = new ArrayList<>();
        for (Item item : myItemMap.keySet()) {
            actions.addAll(item.getActions());
        }
        return actions;
    }

    // TODO: trade with affiliates
    @Override
    public Action getInteraction () {
        return null;
    };

    // Adding for Outcomes, can potentially change later
    // Need to keep method names and signatures similar for reflection
    // since dealing with different data structures
    public int getStat (String statName) {
        return myUnitStats.getStatValue(statName);
    }

    public void setStat (String statName, int statValue) {
        myUnitStats.setStatValue(statName, statValue);
    }
    
    public void hasMoved () {
        this.hasMoved = true;
    }

    @Override
    public int hashCode () {
        final int prime = 31;
        int result = 1;
        result = prime * result + (isControllable ? 1231 : 1237);
        result = prime * result + ((myActiveWeapon == null) ? 0 : myActiveWeapon.hashCode());
        long temp;
        temp = Double.doubleToLongBits(myExperience);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(myHealth);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result + ((myItemMap == null) ? 0 : myItemMap.hashCode());
        result = prime * result + ((myUnitStats == null) ? 0 : myUnitStats.hashCode());
        return result;
    }

    @Override
    public boolean equals (Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        GameUnit other = (GameUnit) obj;
        if (isControllable != other.isControllable) return false;
        if (myActiveWeapon == null) {
            if (other.myActiveWeapon != null) return false;
        }
        else if (!myActiveWeapon.equals(other.myActiveWeapon)) return false;
        if (myTeamName != other.myTeamName) return false;
        if (Double.doubleToLongBits(myExperience) != Double.doubleToLongBits(other.myExperience))
            return false;
        if (Double.doubleToLongBits(myHealth) != Double.doubleToLongBits(other.myHealth))
            return false;
        if (myItemMap == null) {
            if (other.myItemMap != null) return false;
        }
        else if (!myItemMap.equals(other.myItemMap)) return false;
        if (myUnitStats == null) {
            if (other.myUnitStats != null) return false;
        }
        else if (!myUnitStats.equals(other.myUnitStats)) return false;
        return true;
    }

}
