package gameObject.action;

import gameObject.GameObject;
import gameObject.GameUnit;
import gameObject.item.Shop;
import grid.GridConstants;

public class TradeAction extends Action {

    private String myItem;
    private Outcomes myInitiatorOutcomes;
    private Outcomes myReceiverOutcomes;
    
    public TradeAction(String item) {
        super.setName(GridConstants.TRADE+ " "+item);
        myItem = item;
        myInitiatorOutcomes = new Outcomes();
        myReceiverOutcomes = new Outcomes();
    }
    
    @Override
    public void doAction (GameUnit initiator, GameObject receiver) {
        GameUnit receiverUnit = (GameUnit) receiver;
        
        initiator.combatSetItemValue(myItem, receiverUnit.combatGetItemValue(myItem) + initiator.combatGetItemValue(myItem));
        receiverUnit.removeAllItem(myItem);
        
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

}
