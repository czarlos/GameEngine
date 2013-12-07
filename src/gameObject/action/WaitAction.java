package gameObject.action;

import gameObject.GameObject;
import gameObject.GameUnit;
import grid.GridConstants;


public class WaitAction extends Action {

    public WaitAction () {
        super.setName(GridConstants.WAIT);
    }

    @Override
    public void doAction (GameUnit initiator, GameObject receiver) {
        initiator.setActive(false);
    }

    @Override
    public boolean isValid (GameUnit gameUnit, GameObject gameObject) {
        return false;
    }
}
