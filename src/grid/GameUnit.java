package grid;

import gameObject.GameObject;
import gameObject.Stat;
import gameObject.item.Items;
import gameObject.item.Weapon;
import java.util.List;
import action.CombatAction;


/**
 * Game Unit is any unit in the game that can be interacted with,
 * game units can be stationary or can move, these units can have stats
 * and they can hold items.
 * 
 * @author carlosreyes
 * 
 */
public class GameUnit extends GameObject {
    private boolean isControllable;
    private List<Items> myItemsList;
    private Stat myUnitStats;
    private String myAffiliation;
    private Weapon myActiveWeapon;

    // Properties (these can be moved/changed/eliminated)
    private double myHealth;

    public GameUnit (String name,
                     String imagePath,
                     String affiliation,
                     Stat stats,
                     List<Items> items,
                     boolean controllable,
                     int health) {
        super(name, imagePath);
        myAffiliation = affiliation;
        myUnitStats = stats;
        myItemsList = items;
        isControllable = controllable;
        myHealth = health;
    }

    /**
     * Will update the stats of a player holding an
     * item if that item passively updates the players
     * stats, this is particularly evident in armor
     * where armor has no action but simply ups
     * a players stats.
     */
    public void initializeStats () {
        for (Items item : myItemsList) {
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
        for (Items item : myItemsList) {
            if (item.getName().equals(weaponName)) {
                myActiveWeapon = (Weapon) item;
            }
        }
    }

    /**
     * Selects an action from the current active weapon and executes the
     * function of that action.
     * 
     * @param actionName
     */
    public void doAction (CombatAction action, GameUnit other) {
        CombatAction selectedAction = myActiveWeapon.selectAction(action);
        selectedAction.execute(this, other);
    }

    public Stat getStats () {
        return myUnitStats;
    }

    public void setUnitStats (Stat myUnitStats) {
        this.myUnitStats = myUnitStats;
    }

    public Stat getUnitStats () {
        return myUnitStats;

    }

    public String getAffiliation () {
        return myAffiliation;
    }

    public void setAffiliation (String myAffiliation) {
        this.myAffiliation = myAffiliation;
    }

    public boolean isControllable () {
        return isControllable;
    }

    public void setControllable (boolean myControllable) {
        this.isControllable = myControllable;
    }

    public Items getActiveWeapon () {
        return myActiveWeapon;
    }

    public void setActiveWeapon (Items myActiveItem) {
        this.myActiveWeapon = (Weapon) myActiveItem;
    }

    public double getHealth () {
        return myHealth;
    }

    public void setHealth (double d) {
        this.myHealth = d;
    }

}
