package gameObject.action;

import java.util.HashSet;
import java.util.Set;
import gameObject.GameObject;
import gameObject.GameUnit;
import gameObject.item.Chest;
import gameObject.item.Item;
import grid.GridConstants;

public class ChestAction extends Action {

    public ChestAction() {
        super.setName(GridConstants.CHEST);
    }
    
    @Override
    public void doAction (GameUnit initiator, GameObject receiver) {
        Chest chest = (Chest) receiver;
        Set<Item> chestItems = chest.getItems();
        for(Item i : chestItems) {
            initiator.addItem(i);
        }       
        chest.setItems(new HashSet<Item>());  // TODO : THIS 
    }

    @Override
    public boolean isValidAction (GameUnit gameUnit, GameObject gameObject) {
        if(gameObject instanceof Chest) {
            return !((Chest) gameObject).isEmpty();
        }
        return true;
    }

}
