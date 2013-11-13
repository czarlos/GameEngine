package gameObject;

import gameObject.item.*;
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
    private boolean isActive;

    public GameUnit () {
        super();
        myUnitStats = new Stat();
        myUnitStats.makeStat("movement", 3);
        setItemList(new java.util.ArrayList<gameObject.item.Item>()); 
        setActive(false);
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

    @Override
    public boolean isPassable (GameObject unit) {
        return super.isPassable(unit) || ((GameUnit) unit).getAffiliation() == myAffiliation;
    }
    
    public int getTotalStat(String stat){
        int value = myUnitStats.getStatValue(stat);
        for (Item i : myItemList)
            if (i instanceof Equipment)
                value+=((Equipment) i).getModifiers().getStatModifier(stat);
        return value;
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

    public void setActive(boolean active) {
        this.isActive = active;
    }
    
    public boolean getActiveStatus() {
        return this.isActive;
    }
    
 /*   public Properties getProperties () {
        return myProperties;
    }

    public void setProperties (Properties myProperties) {
        this.myProperties = myProperties;
    }*/

    public List<Item> getItemList () {
        return myItemList;
    }

    public void setItemList (List<Item> items) {
        myItemList = items;
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

}