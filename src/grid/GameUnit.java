package grid;

import gameObject.GameObject;
import gameObject.Stat;
import gameObject.item.Items;
import gameObject.item.Weapon;
import java.util.List;

/**
 * Game Unit is any unit in the game that can be interacted with,
 * game units can be stationary or can move, these units can have stats
 * and they can hold items.
 * 
 * @author carlosreyes
 *
 */
public class GameUnit extends GameObject {
    private boolean myControllable;
    private List<Items> myItemsList;
    private Stat myUnitStats;
    private String myAffiliation;
    private Items myActiveWeapon;
    
    public GameUnit (String name, String imagePath) {
        super(name, imagePath);
        // TODO Auto-generated constructor stub
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
     * @param weaponName
     */
    public void selectWeapon(String weaponName) {
        for(Items item : myItemsList) {
            if(item.getName().equals(weaponName)) {
                myActiveWeapon = item;
            }
        }
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
        return myControllable;
    }

    public void setControllable (boolean myControllable) {
        this.myControllable = myControllable;
    }

    public Items getActiveWeapon () {
        return myActiveWeapon;
    }

    public void setActiveWeapon (Items myActiveItem) {
        this.myActiveWeapon = myActiveItem;
    }

}
