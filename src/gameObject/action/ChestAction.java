package gameObject.action;

import java.util.Set;
import gameObject.Chest;
import gameObject.GameObject;
import gameObject.GameUnit;
import gameObject.item.Item;
import grid.GridConstants;


public class ChestAction extends Action {

    public ChestAction () {
        super.setName(GridConstants.CHEST);
    }

    @Override
    public void doAction (GameUnit initiator, GameObject receiver) {
        Chest chest = (Chest) receiver;
        Set<Item> chestItems = chest.getItems();
        for (Item item : chestItems) {
            initiator.combatSetItemValue(item,
                                         initiator.combatGetItemValue(item) +
                                                 chest.getItemAmount(item.getName()));
        }
        chest.removeAllItems();
    }

    @Override
    public boolean isValid (GameUnit gameUnit, GameObject gameObject) {
        if (gameObject == null) { return false; }
        if (gameObject instanceof Chest) { return !((Chest) gameObject).isEmpty(); }
        return false;
    }
}
