package gameObject.action;

import gameObject.GameObject;
import gameObject.GameUnit;
import grid.GridConstants;


public class MoveAction extends Action {

    public MoveAction () {
        super.setName(GridConstants.MOVE);
    }

    @Override
    public void doAction (GameUnit initiator, GameObject receiver) {
    }

    @Override
    public boolean isValid (GameUnit gameUnit, GameObject gameObject) {
        return false;
    }

}
