package gameObject.item;

import java.util.List;
import gameObject.GameObject;
import gameObject.action.Action;
import gameObject.action.ChestAction;


/**
 * Holds a list of items that a unit can retrieve.
 * 
 * @author carlosreyes
 * 
 */
public class Chest extends GameObject {

    private List<Item> myItemList;

    public Chest (List<Item> itemList) {
        myItemList = itemList;
    }

    public List<Item> getItemList () {
        return myItemList;
    }

    public void setItemList (List<Item> itemList) {
        myItemList = itemList;
    }

    
    // TODO: give objects to person
    @Override
    public Action getInteraction () {
        return new ChestAction();
    }

}
