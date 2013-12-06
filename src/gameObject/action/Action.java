package gameObject.action;

import java.util.ArrayList;
import java.util.List;
import view.Customizable;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import gameObject.GameObject;
import gameObject.GameUnit;
import grid.Coordinate;


@JsonAutoDetect
public abstract class Action extends Customizable {
    private List<Coordinate> myAOE;
    private boolean isAround;
    private int myActionRange;

    public Action () {
        List<Coordinate> AOE = new ArrayList<>();
        AOE.add(new Coordinate(0, 1));
        setAround(false);
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

    public boolean isAround () {
        return isAround;
    }

    public void setAround (boolean isAround) {
        this.isAround = isAround;
    }

    public List<Coordinate> getAOE () {
        return myAOE;
    }

    public void setAOE (List<Coordinate> AOE) {
        myAOE = AOE;
    }
}
