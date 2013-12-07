package gameObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import gameObject.action.ChestAction;
import gameObject.item.Item;


/**
 * Holds a set of items that a unit can retrieve.
 * 
 * @author Kevin, Andy
 * 
 */
@JsonAutoDetect
public class Chest extends GameObject {

    @JsonProperty
    protected Map<String, Integer> myItemAmounts;
    protected Set<Item> myItems;

    public Chest () {
        myItems = new HashSet<>();
        myItemAmounts = new HashMap<>();
    }

    public Set<Item> getItems () {
        return myItems;
    }

    public int getItemAmount (String itemName) {
        return myItemAmounts.get(itemName);
    }

    public void setItems (Set<Item> items) {
        myItems = items;
    }

    public void emptyItems () {
        myItems = new HashSet<>();
        myItemAmounts = new HashMap<>();
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
            myItemAmounts.put(item.getName(), myItemAmounts.get(item.getName()) + 1);
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

    public boolean isEmpty () {
        return myItems.isEmpty();
    }

    @Override
    @JsonIgnore
    public List<String> getInteractions () {
        if (myItems.isEmpty()) { return null; }
        List<String> actions = new ArrayList<String>();
        actions.add(new ChestAction().getName());
        return actions;
    }

    @Override
    public List<String> generateDisplayData () {
        List<String> displayData = super.generateDisplayData();
        displayData.add("<html><b>Inventory:</b><html>");
        if (!myItems.isEmpty()) {
            for (Item item : myItems) {
                displayData.add("   " + item.getName() + ": " + myItemAmounts.get(item.getName()));
            }
        }
        else {
            displayData.add("    Nothing");
        }
        return displayData;
    }

}
