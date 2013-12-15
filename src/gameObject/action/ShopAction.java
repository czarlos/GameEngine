package gameObject.action;

import gameObject.GameObject;
import gameObject.GameUnit;
import gameObject.Item;
import gameObject.Shop;
import grid.GridConstants;


public class ShopAction extends TradeAction {

    public ShopAction () {
        super.setName(GridConstants.SHOP);
    }

    public ShopAction (String itemName) {
        init(itemName);
    }

    @Override
    protected void setItem (String itemName) {
        setName(GridConstants.SHOP + " " + itemName);
        myItem = itemName;
    }

    @Override
    public boolean isValid (GameUnit gameUnit, GameObject gameObject) {
        if (gameObject == null) {
            return false;
        }
        else if (gameObject instanceof Shop) { return !((Shop) gameObject).isEmpty(); }
        return false;
    }

    @Override
    public void doAction (GameUnit initiator, GameObject receiver) {
        Shop receiverShop = (Shop) receiver;
        Item tradeItem = receiverShop.getItem(myItem);
        if (tradeItem != null) {
            initiator.combatSetItemValue(tradeItem,
                                         receiverShop.getItemAmount(tradeItem.getName()) +
                                                 initiator.combatGetItemValue(tradeItem));
            receiverShop.removeAllOfAnItem(tradeItem);
        }
    }
}
