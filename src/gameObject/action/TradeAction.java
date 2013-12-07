package gameObject.action;

import gameObject.GameObject;
import gameObject.GameUnit;
import gameObject.item.Item;
import grid.GridConstants;


public class TradeAction extends Action {
    protected String myItem;

    public TradeAction () {
        super.setName(GridConstants.TRADE);
    }

    public TradeAction (String itemName) {
        init(itemName);
    }

    @Override
    public void doAction (GameUnit initiator, GameObject receiver) {
        GameUnit receiverUnit = (GameUnit) receiver;
        Item tradeItem = receiverUnit.getItem(myItem);
        initiator.combatSetItemValue(tradeItem, receiverUnit.combatGetItemValue(tradeItem) +
                                                initiator.combatGetItemValue(tradeItem));
        receiverUnit.removeAllItem(tradeItem);
    }

    @Override
    public boolean isValid (GameUnit gameUnit, GameObject gameObject) {
        if (gameObject == null) { return false; }
        else if (gameObject instanceof GameUnit) {
            if (gameUnit.getAffiliation().equals(((GameUnit) gameObject).getAffiliation())) {
                if (((GameUnit) gameObject).getItemAmount(myItem) != 0) { return true; }
            }
        }
        return false;
    }

    protected void init (String itemName) {
        setItem(itemName);
    }

    protected void setItem (String itemName) {
        setName(GridConstants.TRADE + " " + itemName);
        myItem = itemName;
    }

}
