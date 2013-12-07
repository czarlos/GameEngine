package gameObject.action;

import gameObject.GameObject;
import gameObject.GameUnit;
import gameObject.item.Item;
import gameObject.item.Shop;
import grid.GridConstants;


public class TradeAction extends Action {

    private String myItem;

    public TradeAction () {
        super.setName(GridConstants.TRADE);
    }

    public TradeAction (String string) {
        init(string);
    }

    @Override
    public void doAction (GameUnit initiator, GameObject receiver) {
        System.out.println("doing shit on " + myItem);
        GameUnit receiverUnit = (GameUnit) receiver;
        Item tradeItem = receiverUnit.getItem(myItem);
        System.out.println("before: " + receiverUnit.combatGetItemValue(tradeItem) +
                           initiator.combatGetItemValue(tradeItem));
        initiator.combatSetItemValue(tradeItem, receiverUnit.combatGetItemValue(tradeItem) +
                                                initiator.combatGetItemValue(tradeItem));
        receiverUnit.removeAllItem(tradeItem);
        System.out.println("after: " + receiverUnit.combatGetItemValue(tradeItem) +
                           initiator.combatGetItemValue(tradeItem));
    }

    @Override
    public boolean isValid (GameUnit gameUnit, GameObject gameObject) {
        if (gameObject == null) { return false; }
        if (gameObject instanceof Shop) {

        }
        else if (gameObject instanceof GameUnit) {
            if (gameUnit.getAffiliation().equals(((GameUnit) gameObject).getAffiliation())) {
                if (((GameUnit) gameObject).getItemAmount(myItem) != 0) { return true; }
            }
        }
        return false;
    }

    public void init (String itemName) {
        setItem(itemName);
    }

    private void setItem (String itemName) {
        setName(GridConstants.TRADE + " " + itemName);
        myItem = itemName;
    }

}
