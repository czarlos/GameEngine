package gameObject;

import game.ImageManager;
import gameObject.action.Outcome;
import gameObject.action.Outcomes;
import gameObject.action.TradeAction;
import gameObject.item.*;
import grid.GridConstants;
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
 * Game Unit is any unit in the game that can be interacted with, game units can
 * be stationary or can move, these units can have stats and they can hold
 * items.
 * 
 * @author carlosreyes
 * 
 */

@JsonAutoDetect
public class GameUnit extends GameObject {

    @JsonProperty
    private Map<String, Integer> myItemAmounts;
    private Set<Item> myItems;
    private Stats myStats;
    private String myTeamName;
    private Item myActiveWeapon;
    private boolean hasMoved;

    public GameUnit () {
        myItems = new HashSet<>();
        myItemAmounts = new HashMap<>();
        myStats = new Stats();
    }

    // should ONLY be called by stage when adding units to a team
    public void setAffiliation (String affiliation) {
        myTeamName = affiliation;
    }

    public String getAffiliation () {
        return myTeamName;
    }

    /**
     * Sets the Game units active weapon to the weapon with a given string name.
     * 
     * @param weaponName The string which represents a weapon
     */
    public void selectWeapon (String weaponName) {
        for (Item item : myItems) {
            if (item.getName().equals(weaponName)) {
                myActiveWeapon = item;
            }
        }
    }

    /**
     * Takes an item and adds it to the list of items, adding to the stats of
     * the unit as it adds in an item.
     * 
     * @param itemName The name of the item, not a string
     */
    @Override
    public void addItem (Item item) {
        if (myItems.add(item)) {
            myItemAmounts.put(item.getName(), 1);
        }
        else {
            myItemAmounts.put(item.getName(), myItemAmounts.get(item) + 1);
        }
    }

    public void removeItem (String itemName) {
        int amount = myItemAmounts.get(itemName);
        if (amount > 1) {
            myItemAmounts.put(itemName, amount - 1);
        }
        else {
            myItemAmounts.remove(itemName);
            for (Item item : myItems) {
                if (item.equals(itemName)) {
                    myItems.remove(item);
                }
            }
        }
    }
    
    public void removeAllItem(String itemName) {
        myItems.remove(itemName);
        myItemAmounts.remove(itemName);
    }

    @Override
    public boolean isPassable (GameUnit unit) {
        return unit.getAffiliation().equals(myTeamName);
    }

    /**
     * Gets the total stat value for a given stat of a character after all of
     * the item's stats have been applied. Assuming initializeStats has been
     * called.
     * 
     * @param stat The stat that we want to see
     * @return
     */
    public int getTotalStat (String stat) {
        int value = myStats.getStatValue(stat);
        for (Item item : myItems) {
            // TODO: Add call to get item here from manager
            value += item.getStat(stat);
        }
        return value;
    }

    // Adding for Outcomes, can potentially change later
    // Need to keep method names and signatures similar for reflection
    // since dealing with different data structures
    public int combatGetStatValue (String statName) {
        return getTotalStat(statName);
    }

    public void combatSetStatValue (String statName, int statValue) {
        int baseStatDiff = getTotalStat(statName) - myStats.getStatValue(statName);
        myStats.modExisting(statName, statValue - baseStatDiff);
    }

    public int combatGetItemValue (String itemName) {
        return (myItemAmounts.get(itemName) == null ? 0 : myItemAmounts.get(itemName));
    }

    public void combatSetItemValue (String itemName, int itemValue) {
        if (myItems.add(itemName)) {
            myItemAmounts.put(itemName, itemValue);
        }
        else {
            myItemAmounts.put(itemName, itemValue);
        }
    }

    public Item getActiveWeapon () {
        return myActiveWeapon;
    }

    public void setActiveWeapon (Item activeItem) {
        myActiveWeapon = activeItem;
    }

    @Override
    public void setActive (boolean active) {
        hasMoved = !active;
        isActive = active;
        myImage = isActive ? ImageManager.getHightlightedTileImage(myImagePath)
                          : ImageManager.getImage(myImagePath);
    }

    public void hasMoved () {
        hasMoved = true;
    }

    @JsonIgnore
    public List<String> getActions () {
        List<String> actions = new ArrayList<>();
        if (isActive) {
            if (!hasMoved) {
                actions.add(GridConstants.MOVE);
            }
            actions.add(GridConstants.WAIT);
            for (Item item : myItems) {
                actions.addAll(item.getActions());
            }
        }
        return actions;
    }

    /**
     * Generates the List of Strings that the unit will display to the user
     */
    @Override
    public List<String> generateDisplayData () {
        List<String> displayData = super.generateDisplayData();
        displayData.add("Team: " + myTeamName);
        displayData.add("Stats: ");
        displayData.add("    health: " + getTotalStat("health") + " / " +
                        myStats.getStatValue("maxhealth"));
        for (String stat : myStats.getStatNames()) {
            if (!stat.equals("health") && !stat.equals("maxhealth")) {
                displayData.add("    " + stat + ": " + getTotalStat(stat));
            }
        }
        displayData.add("Equipment: ");
        for (Item item : myItems) {
            displayData.add("    " + item + ": " + getItemAmount(item.getName()));
        }
        setDisplayData(displayData);
        return displayData;
    }

    // TODO: trade with affiliates
    @Override
    public List<String> getInteractions () {
        List<String> interactions = new ArrayList<>();
        
        for (Item item : myItems) {
            interactions.add(GridConstants.TRADE+ " "+item.getName());
        }

        return interactions;
    };

    @JsonIgnore
    public int getItemAmount (String itemName) {
        for (Item item : myItems) {
            if (item.equals(itemName)) { return myItemAmounts.get(itemName); }
        }
        return 0;
    }

    public void syncActionsWithMaster (Map<String, String> nameTranslations,
                                       List<String> removedActions) {
        for (Item item : myItems) {
         // TODO: Add call to get item here from manager
            for (String removedAction : removedActions) {
                // TODO: Add call to get item here from manager
                if (item.getActions().contains(removedAction)) {
                    item.removeAction(removedAction);
                }
            }
            for (String action : item.getActions()) {
                // TODO: Add call to get item here from manager
                item.removeAction(action);
                item.addAction(nameTranslations.get(action));
            }
        }
    }

    public void setStats (Stats stats) {
        myStats = new Stats(stats);
    }

    public Stats getStats () {
        return myStats;
    }

    public int getStat (String statName) {
        return myStats.getStatValue(statName);
    }

    public void setStat (String statName, int statValue) {
        myStats.modExisting(statName, statValue);
    }

    public void setItems (Set<Item> itemList) {
        myItems = itemList;
    }

    public Set<Item> getItems () {
        return myItems;
    }

}
