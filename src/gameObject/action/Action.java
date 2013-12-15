package gameObject.action;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import gameObject.Customizable;
import gameObject.GameObject;
import gameObject.GameUnit;


@JsonAutoDetect
public abstract class Action extends Customizable {
    protected int myActionRange;

    public Action () {
        myActionRange = 1;
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
