package gameObject.action;

import gameObject.GameObject;
import gameObject.GameUnit;


public class WaitAction extends Action {
    public final static String NAME = "WaitAction";

    public WaitAction () {
        super.setName(NAME);
    }

    @Override
    public void doAction (GameUnit initiator, GameObject receiver) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean isValidAction (GameUnit gameUnit, GameObject gameObject) {
        // TODO Auto-generated method stub
        return false;
    }

}
