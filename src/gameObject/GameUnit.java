package gameObject;

import gameObject.action.Action;
import gameObject.action.CombatAction;
import gameObject.item.*;
import java.util.ArrayList;
import java.util.List;
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
    private List<Item> myItemList;
    private Stats myStats;
    private String myAffiliation;
    private Weapon myActiveWeapon;
    private double myMaxHealth;
    private double myExperience;
    private boolean isActive;

    // TODO: this is in make defaults. doesn't have to be here then?
    // reads defaults from JSON. To add/test new defaults, edit MakeDefaults.java
    public GameUnit () {
        myItemList = new ArrayList<Item>();
        myStats = new Stats();
        myAffiliation = "";
    }

    // should ONLY be called by stage when adding units to a team
    public void setAffiliation (String affiliation) {
        myAffiliation = affiliation;
    }

    public String getAffiliation () {
        return myAffiliation;
    }

    /**
     * Will update the stats of a player holding an
     * item if that item passively updates the players
     * stats, this is particularly evident in armor
     * where armor has no action but simply ups
     * a players stats.
     */
    public void initializeStats () {
        for (Item item : myItemList) {
            item.statEffect(this);
        }
    }

    /**
     * Sets the Game units active weapon to the weapon
     * with a given string name.
     * 
     * @param weaponName - The string which represents a weapon
     */
    public void selectWeapon (String weaponName) {
        for (Item item : myItemList) {
            if (item.getName().equals(weaponName)) {
                myActiveWeapon = (Weapon) item;
            }
        }
    }

    /**
     * Selects an action from the current active weapon and executes the
     * function of that action. Takes in the action chosen to be executed
     * and the unit that the action will be used on.
     * 
     * @param actionName - The name of an action, not a string
     * @param other - The unit onto which the action is executed
     */
    public void doAction (CombatAction action, GameUnit other) {
        CombatAction selectedAction = myActiveWeapon.selectAction(action);
        selectedAction.execute(this, other);
    }

    /**
     * Takes an item and adds it to the list, if item is Equipment,
     * then we modify the characters stats according to the stats of
     * the item.
     * 
     * @param itemName - The name of the item, not a string
     */
    public void addItem (Item itemName) {
        if (itemName instanceof Equipment) {
            for (String stat : ((Equipment) itemName).getModifiers().getStatModifierMap().keySet()) {
                int statVal = this.getStats().getStatValue(stat);
                statVal += ((Equipment) itemName).getModifiers().getStatModifier(stat);
                this.getStats().modExisting(stat, statVal);
            }
        }
        myItemList.add(itemName);
    }

    /**
     * Removes a particular item from the units itemList, ensures that upon removal
     * the unit's stats get decremented accordingly.
     * 
     * @param itemName - The name of the item, not a string
     */
    public void removeItem (Item itemName) {
        if (itemName instanceof Equipment) {
            for (String stat : ((Equipment) itemName).getModifiers().getStatModifierMap().keySet()) {
                int statVal = this.getStats().getStatValue(stat);
                statVal -= ((Equipment) itemName).getModifiers().getStatModifier(stat);
                this.getStats().modExisting(stat, statVal);
            }
        }
        myItemList.remove(itemName);
    }

    @Override
    public boolean isPassable (GameUnit unit) {
        unit.getAffiliation();
        System.out.println(myAffiliation);
        return super.isPassable(unit) || unit.getAffiliation().equals(myAffiliation);
    }

    /**
     * Gets the total stat value for a given stat of a character
     * after all of the item's stats have been applied.
     * 
     * @param stat - The stat that we want to see
     * @return
     */
    public int getTotalStat (String stat) {
        int value = myStats.getStatValue(stat);
        for (Item i : myItemList)
            if (i instanceof Equipment)
                value += ((Equipment) i).getModifiers().getStatModifier(stat);
        return value;
    }

    /**
     * Initializes an attack from this unit to another unit based
     * on a weapon, attack, and action chosen by the user. The execute method
     * called by doAction executes the attack.
     * 
     * @param other - The unit being attacked.
     * @param weaponName - The weapon being used
     * @param actionName - The action chosen from the weapon being used
     */
    public void attack (GameUnit other, String weaponName, CombatAction actionName) {
        this.selectWeapon(weaponName);
        this.doAction(actionName, other);
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
        this.isActive = active;
    }

    public boolean getActiveStatus () {
        return this.isActive;
    }

    public double getHealth () {
        return myMaxHealth;
    }

    public void setHealth (double myHealth) {
        this.myMaxHealth = myHealth;
    }

    public double getExperience () {
        return myExperience;
    }

    public void setExperience (double myExperience) {
        this.myExperience = myExperience;
    }

    public List<Item> getItemList () {
        return myItemList;
    }

    // TODO: get rid of this? unit shouldn't know about defenders. that's grid.
    public List<Action> getValidActions (GameUnit defender) {
        List<Action> validActions = new ArrayList<>();
        for (Item i : myItemList) {
            if (i instanceof Weapon) {
                List<CombatAction> tempActions = ((Weapon) i).getActionList();
                for (CombatAction ca : tempActions) {
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
        List<Action> actions = new ArrayList<>();
        for (Item item : myItemList) {
            if (item instanceof Weapon) {
                actions.addAll(((Weapon) item).getActionList());
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
        displayData.add("Affiliation: " + myAffiliation);
        displayData.add("");
        displayData.add("Equipped Item: " + myActiveWeapon.getName());
        displayData.add("");
        displayData.add("Stats: ");
        displayData
                .add("Health: " + getTotalStat(GameObjectConstants.HEALTH) + " / " + myMaxHealth);
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

    public int getItem (String itemName) {
        for (Item i : myItemList) {
            if (i.getName().equals(itemName)) { return i.getAmount(); }
        }
        return 0;
    }

    public void setItem (String itemName, int itemValue) {
        for (Item i : myItemList) {
            if (i.getName().equals(itemName)) {
                i.setAmount(itemValue);
            }
        }
    }

    public void setItemList (List<Item> myItemList) {
        this.myItemList = myItemList;
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
        temp = Double.doubleToLongBits(myMaxHealth);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result + ((myItemList == null) ? 0 : myItemList.hashCode());
        result = prime * result + ((myStats == null) ? 0 : myStats.hashCode());
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
        if (myAffiliation != other.myAffiliation) return false;
        if (Double.doubleToLongBits(myExperience) != Double.doubleToLongBits(other.myExperience))
            return false;
        if (Double.doubleToLongBits(myMaxHealth) != Double.doubleToLongBits(other.myMaxHealth))
            return false;
        if (myItemList == null) {
            if (other.myItemList != null) return false;
        }
        else if (!myItemList.equals(other.myItemList)) return false;
        if (myStats == null) {
            if (other.myStats != null) return false;
        }
        else if (!myStats.equals(other.myStats)) return false;
        return true;
    }

}
