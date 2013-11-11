package gameObject;

import gameObject.item.*;
import grid.GridConstants;
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
    private List<Item> myItemList;
    private Stat myUnitStats;
    private String myAffiliation;
    private Weapon myActiveWeapon;
    private Properties myProperties;

    /**
     * default. currently using for testing
     * eventually put on data sheet JSON and pull from there
     */
    public GameUnit () {
        myName = GridConstants.DEFAULT_UNIT_NAME;
        setImage(GridConstants.DEFAULT_UNIT_PATH);
        myAffiliation = "test";
        myUnitStats = new Stat() {{makeStat("movement", 3);}};
    }
    
    public GameUnit (String name,
                     String imagePath,
                     String affiliation,
                     Stat stats,
                     List<Item> item,
                     boolean controllable,
                     Properties properties) {
        super(name, imagePath);
        myAffiliation = affiliation;
        myUnitStats = stats;
        myItemList = item;
        isControllable = controllable;
        myProperties = properties;
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
        return super.isPassable(unit) || ((GameUnit) unit).getAffiliation().equals(myAffiliation);
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

    public Item getActiveWeapon () {
        return myActiveWeapon;
    }

    public void setActiveWeapon (Item myActiveItem) {
        this.myActiveWeapon = (Weapon) myActiveItem;
    }

    public Properties getProperties () {
        return myProperties;
    }

    public void setProperties (Properties myProperties) {
        this.myProperties = myProperties;
    }

}
