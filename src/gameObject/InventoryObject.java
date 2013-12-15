package gameObject;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * Objects that have an inventory
 * 
 * @author Leevi, Kevin, Andy
 * 
 */
@JsonAutoDetect
public class InventoryObject extends GameObject {
    protected Map<String, Integer> myItemAmounts;
    protected Set<Item> myItems;

    public InventoryObject () {
        myItems = new HashSet<>();
        myItemAmounts = new HashMap<>();
    }

    public Set<Item> getItems () {
        return myItems;
    }

    public void setItems (Set<Item> items) {
        myItems = items;
    }

    @JsonIgnore
    public int getItemAmount (String itemName) {
        for (Item item : myItems) {
            if (item.getName().equals(itemName)) { return myItemAmounts.get(itemName); }
        }
        return 0;
    }

    /**
     * Takes an item and adds it to the list of items, adding to the stats of
     * the unit as it adds in an item.
     * 
     * @param itemName The name of the item, not a string
     */
    public void addItem (Item item) {
        if (myItems.add(item)) {
            myItemAmounts.put(item.getName(), 1);
        }
        else {
            myItemAmounts.put(item.getName(), myItemAmounts.get(item.getName()) + 1);
        }
    }

    public Item getItem (String itemName) {
        if (myItems.isEmpty()) { return null; }
        for (Item item : myItems) {
            if (itemName.equals(item.getName())) { return item; }
        }
        return null;
    }

    /**
     * Returns a boolean of if the inventory of the object is empty
     * 
     * @return boolean of if the inventory is empty
     */
    public boolean isEmpty () {
        return myItems.isEmpty();
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

    public void removeAllOfAnItem (Item item) {
        myItems.remove(item);
        myItemAmounts.remove(item.getName());
    }

    public void removeAllItems () {
        myItems = new HashSet<>();
        myItemAmounts = new HashMap<>();
    }

    public Map<String, Integer> getItemAmounts () {
        return myItemAmounts;
    }

    public void setItemAmounts (Map<String, Integer> map) {
        myItemAmounts = map;
    }

    @Override
    public List<String> generateDisplayData () {
        List<String> displayData = super.generateDisplayData();
        displayData.add("<b>Inventory:</b>");
        if (!myItems.isEmpty()) {
            for (Item item : myItems) {
                displayData.add("&nbsp; &nbsp; &nbsp;" + item.getName() + ": " +
                                myItemAmounts.get(item.getName()));
            }
        }
        else {
            displayData.add("&nbsp; &nbsp; &nbsp; Nothing");
        }
        return displayData;
    }
}
