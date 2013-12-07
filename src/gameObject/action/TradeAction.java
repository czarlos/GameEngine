package gameObject.action;

import gameObject.GameObject;
import gameObject.GameUnit;
import gameObject.item.Item;
import gameObject.item.Shop;
import grid.GridConstants;

public class TradeAction extends Action {

    private String myItem;
    private Outcomes myInitiatorOutcomes;
    private Outcomes myReceiverOutcomes;
    
    public TradeAction() {
        super.setName(GridConstants.TRADE);
    }
    
    public TradeAction(String string) {
        init(string);
    }
    
    @Override
    public void doAction (GameUnit initiator, GameObject receiver) {
        GameUnit receiverUnit = (GameUnit) receiver;
        Item tradeItem = receiverUnit.getItem(myItem);
        initiator.combatSetItemValue(tradeItem, receiverUnit.combatGetItemValue(tradeItem) + initiator.combatGetItemValue(tradeItem));
        receiverUnit.removeAllItem(tradeItem);      
    }

    @Override
    public boolean isValid (GameUnit gameUnit, GameObject gameObject) {
        if (gameObject == null) {
            return false;
        }
        if (gameObject instanceof Shop) {
            
        }
        else if (gameObject instanceof GameUnit) {
            if (gameUnit.getAffiliation().equals(((GameUnit) gameObject).getAffiliation())) {
                return true;
            }
        }
        return false;
    }
    
    public Outcomes getInitiatorOutcomes () {
        return myInitiatorOutcomes;
    }

    public void setInitiatorOutcomes (Outcomes outcomes) {
        myInitiatorOutcomes = outcomes;
    }

    public Outcomes getReceiverOutcomes () {
        return myReceiverOutcomes;
    }

    public void setReceiverOutcomes (Outcomes outcomes) {
        myReceiverOutcomes = outcomes;
    }
    
    public void init (String itemName) {
        setItem(itemName);
        myInitiatorOutcomes = new Outcomes();
        myReceiverOutcomes = new Outcomes();
    }
    
    private void setItem (String itemName) {
        setName(GridConstants.TRADE + " " + itemName);
        myItem = itemName;
    }

}
