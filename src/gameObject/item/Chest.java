package gameObject.item;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import gameObject.GameObject;
import gameObject.action.ChestAction;


/**
 * Holds a list of items that a unit can retrieve.
 * 
 * @author Kevin, Andy
 * 
 */
public class Chest extends GameObject {

    private List<Item> myItems;

    public Chest () {
        myItems = new ArrayList<>();
    }

    public List<Item> getItemList () {
        return myItems;
    }

    public void setItems (List<Item> items) {
        myItems = items;
    }
    
    public boolean isEmpty() {
        return myItems.isEmpty();
    }

    @Override
    @JsonIgnore
    public List<String> getInteractions () {
        List<String> actions = new ArrayList<String>();
        actions.add(new ChestAction().getName());
        return actions;
    }
    
    @Override
    public List<String> generateDisplayData () {
        List<String> displayData = super.generateDisplayData();
        displayData.add("Inventory:");
        if (!myItems.isEmpty()) {
            for (Item item: myItems) {
                displayData.add(item.getName());
            }
        }
        else {
            displayData.add("    Nothing");
        }
        return displayData;
    }

}
