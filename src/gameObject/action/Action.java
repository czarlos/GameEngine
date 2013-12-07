package gameObject.action;

import view.Customizable;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import gameObject.GameObject;
import gameObject.GameUnit;


@JsonAutoDetect
public abstract class Action extends Customizable {
    private int myActionRange;

    public Action () {
    }

    public void setActionRange (int actionRange) {
        myActionRange = actionRange;
    }

    public int getActionRange () {
        return myActionRange;
    }

    public abstract void doAction (GameUnit initiator, GameObject receiver);

    public abstract boolean isValidAction (GameUnit gameUnit,
                                           GameObject gameObject);
}
