package gameObject.action;

import java.util.Set;
import gameObject.GameObject;
import gameObject.GameUnit;
import gameObject.item.Chest;
import grid.GridConstants;

public class ChestAction extends Action {

    public ChestAction() {
        super.setName(GridConstants.CHEST);
        myActionRange = 1;
    }
    
    @Override
    public void doAction (GameUnit initiator, GameObject receiver) {
        Chest chest = (Chest) receiver;
        Set<String> chestItems = chest.getItems();
        for(String item : chestItems) {
            initiator.combatSetItemValue(item, initiator.combatGetItemValue(item) + chest.getItemAmount(item));
        }       
        chest.emptyItems();
    }

    @Override
    public boolean isValidAction (GameUnit gameUnit, GameObject gameObject) {
        if(gameObject instanceof Chest) {
            return !((Chest) gameObject).isEmpty();
        }
        return true;
    }

}
