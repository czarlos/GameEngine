package gameObject.action;

import gameObject.GameObject;
import gameObject.GameUnit;
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
    }

    @Override
    public boolean isValidAction (GameUnit gameUnit, GameObject gameObject) {
        return true;
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
