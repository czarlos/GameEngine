package gameObject;

import gameObject.action.Action;
import gameObject.action.CombatAction;
import gameObject.item.*;
import grid.Coordinate;
import grid.Grid;
import grid.GridConstants;
import java.util.ArrayList;
import java.util.List;
import utils.UnitUtilities;
import com.fasterxml.jackson.annotation.JsonAutoDetect;


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
    private Stat myUnitStats;
    private int myAffiliation;
    private Weapon myActiveWeapon;
    private double myHealth;
    private double myExperience;
    private boolean isActive;
    protected Coordinate myGridPosition;

    // reads defaults from JSON. To add/test new defaults, edit MakeDefaults.java
    public GameUnit () {
        super();
        myUnitStats = new Stat();
        myUnitStats.setStatValue("movement", 3);
        setItemList(new java.util.ArrayList<gameObject.item.Item>());
        myName = GridConstants.DEFAULT_UNIT_NAME;
        setImagePath(GridConstants.DEFAULT_UNIT_PATH);
        myAffiliation = 0;
        myUnitStats = new Stat() {
            {
                setStatValue("movement", 3);
            }
        };
    }

    public GameUnit (String name,
                     String imagePath,
                     int affiliation,
                     Stat stats,
                     List<Item> item,
                     boolean controllable) {
        super();
        myAffiliation = affiliation;
        myUnitStats = stats;
        myItemList = item;
        isControllable = controllable;
        // myUnitStats.makeStat("movement", 3);
        // setItemList(new java.util.ArrayList<gameObject.item.Item>());
        // setActive(false);
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
                int statVal = this.getUnitStats().getStatValue(stat);
                statVal += ((Equipment) itemName).getModifiers().getStatModifier(stat);
                this.getUnitStats().setStatValue(stat, statVal);
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
                int statVal = this.getUnitStats().getStatValue(stat);
                statVal -= ((Equipment) itemName).getModifiers().getStatModifier(stat);
                this.getUnitStats().setStatValue(stat, statVal);
            }
        }
        myItemList.remove(itemName);
    }

    @Override
    public boolean isPassable (GameObject unit) {
        return super.isPassable(unit) || ((GameUnit) unit).getAffiliation() == myAffiliation;
    }

    /**
     * Gets the total stat value for a given stat of a character
     * after all of the item's stats have been applied.
     * @param stat - The stat that we want to see
     * @return
     */
    public int getTotalStat (String stat) {
        int value = myUnitStats.getStatValue(stat);
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
     * Moves this game unit to the coordinates of the other game unit given.
     * Moves the character only a given number of spaces per turn.
     * The string 'movement' must be fed in by the user to specify which
     * stat is responsible for movement/range.
     * Note: Change this to use the a* path finding when it is done.
     * 
     * @param other - The opponent
     * @param movement - The range of movement of this unit
     */
    public void snapToOpponent (GameUnit other) {
        this.getUnitStats().getStatValue(GameObjectConstants.MOVEMENT);

        // These will be used at a later implementation
        Coordinate otherPosition = other.getGridPosition();
        otherPosition.getX();
        otherPosition.getY();

        this.setGridPosition(otherPosition);

    }

    /**
     * This unit searches for the closest unit on the grid
     * 
     * @param opponents - List of opponents
     * @return
     */
    public GameUnit findClosestOpponent (List<GameUnit> opponents) {
        GameUnit closest = null;
        double distance = 0;
        for (GameUnit opponent : opponents) {
            if (closest == null) {
                closest = opponent;
                distance =
                        UnitUtilities.calculateLength(this.getGridPosition(),
                                                      opponent.getGridPosition());
            }
            else if (UnitUtilities.calculateLength(this.getGridPosition(),
                                                   opponent.getGridPosition()) < distance) {
                closest = opponent;
                distance =
                        UnitUtilities.calculateLength(this.getGridPosition(),
                                                      opponent.getGridPosition());
            }
        }

        return closest;
    }
    
    /**
     * Trade allows one unit to swap an item with another unit, no matter
     * what team they are affiliated with. Note: as of this implementation
     * any character will trade with you for anything you want, a system must
     * be implemented which allows the other unit to determine what trades are
     * appropriate.
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

    public Coordinate getGridPosition () {
        return myGridPosition;
    }

    public void setGridPosition (Coordinate gridPosition) {
        this.myGridPosition = gridPosition;
    }

    public void setUnitStats (Stat myUnitStats) {
        this.myUnitStats = myUnitStats;
    }

    public Stat getUnitStats () {
        return myUnitStats;
    }

    public int getAffiliation () {
        return myAffiliation;
    }

    public void setAffiliation (int myAffiliation) {
        this.myAffiliation = myAffiliation;
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

    public List<Item> getItemList () {
        return myItemList;
    }

    public List<Action> getValidActions (Grid grid, GameUnit defender) {
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

    // TODO: trade with affiliates
    @Override
    public Action getInteraction(){
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
        result = prime * result + myAffiliation;
        long temp;
        temp = Double.doubleToLongBits(myExperience);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(myHealth);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result + ((myItemList == null) ? 0 : myItemList.hashCode());
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
        if (myAffiliation != other.myAffiliation) return false;
        if (Double.doubleToLongBits(myExperience) != Double.doubleToLongBits(other.myExperience))
            return false;
        if (Double.doubleToLongBits(myHealth) != Double.doubleToLongBits(other.myHealth))
            return false;
        if (myItemList == null) {
            if (other.myItemList != null) return false;
        }
        else if (!myItemList.equals(other.myItemList)) return false;
        if (myUnitStats == null) {
            if (other.myUnitStats != null) return false;
        }
        else if (!myUnitStats.equals(other.myUnitStats)) return false;
        return true;
    }

}
