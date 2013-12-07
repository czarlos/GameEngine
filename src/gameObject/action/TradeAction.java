package gameObject.action;

import gameObject.GameObject;
import gameObject.GameUnit;
import grid.GridConstants;

public class TradeAction extends Action {

    public TradeAction() {
        super.setName(GridConstants.TRADE);
    }
    
    @Override
    public void doAction (GameUnit initiator, GameObject receiver) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean isValidAction (GameUnit gameUnit, GameObject gameObject) {
        return true;
    }

}
