package gameObject;

import gameObject.item.*;
import grid.Coordinate;
import grid.GridConstants;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import action.CombatAction;


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
    private Properties myProperties;
    private boolean isActive;
    private Coordinate myGridPosition;

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
                     boolean controllable,
                     Properties properties) {
        super();
        myAffiliation = affiliation;
        myUnitStats = stats;
        myItemList = item;
        isControllable = controllable;
        myProperties = properties;
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
     * @param weaponName
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
     * @param actionName
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
     * @param itemName
     */
    public void addItem (Item itemName) {
        if (itemName instanceof Equipment) {
            for (String stat : ((Equipment) itemName).getModifiers().getStatModifierMap().keySet()) {
                int statVal = this.getStats().getStatValue(stat);
                statVal += ((Equipment) itemName).getModifiers().getStatModifier(stat);
                this.getStats().setStatValue(stat, statVal);
            }
        }
        myItemList.add(itemName);
    }

    /**
     * Removes a particular item from the units itemList, ensures that upon removal
     * the unit's stats get decremented accordingly.
     * 
     * @param itemName
     */
    public void removeItem (Item itemName) {
        if (itemName instanceof Equipment) {
            for (String stat : ((Equipment) itemName).getModifiers().getStatModifierMap().keySet()) {
                int statVal = this.getStats().getStatValue(stat);
                statVal -= ((Equipment) itemName).getModifiers().getStatModifier(stat);
                this.getStats().setStatValue(stat, statVal);
            }
        }
        myItemList.remove(itemName);
    }

    @Override
    public boolean isPassable (GameObject unit) {
        return super.isPassable(unit) || ((GameUnit) unit).getAffiliation() == myAffiliation;
    }

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
     * @param other
     */
    public void attack (GameUnit other, String weaponName, CombatAction actionName) {
        this.selectWeapon(weaponName);
        this.doAction(actionName, other);        
    }
    
    /**
     * Moves this game unit to the coordinates of the other game unit given.
     * Moves the character only a given number of spaces per turn.
     * Note: Change this to use the a* path finding when it is done.
     * @param other
     */
    public void snapToOpponent (GameUnit other) {
        
    	Coordinate otherPosition = other.getGridPosition();
    	this.setGridPosition(otherPosition);
        
    }

    private Coordinate getGridPosition() {
		return myGridPosition;
	}
    private void setGridPosition( Coordinate gridPosition ) {
    	this.myGridPosition = gridPosition;
    }

	public void setUnitStats (Stat myUnitStats) {
        this.myUnitStats = myUnitStats;
    }

    public Stat getStats () {
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

    public Item getActiveWeapon () {
        return myActiveWeapon;
    }

    public void setActiveWeapon (Item myActiveItem) {
        this.myActiveWeapon = (Weapon) myActiveItem;
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

    public void setItemList (List<Item> myItemList) {
        this.myItemList = myItemList;
    }

    public Properties getProperties () {
        return myProperties;
    }

    public void setProperties (Properties myProperties) {
        this.myProperties = myProperties;
    }

}
