package gameObject.action;

import view.Customizable;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import gameObject.GameObject;
import gameObject.GameUnit;
import grid.GridConstants;


@JsonAutoDetect
public abstract class Action extends Customizable {
    protected int myActionRange;

    public Action () {
        myActionRange = GridConstants.ACTIONRANGE;
    }

    public void setActionRange (int actionRange) {
        myActionRange = actionRange;
    }

    public int getActionRange () {
        return myActionRange;
    }

    public abstract void doAction (GameUnit initiator, GameObject receiver);

    public abstract boolean isValid (GameUnit gameUnit, GameObject gameObject);
}
