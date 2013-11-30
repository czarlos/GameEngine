package gameObject.action;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import gameObject.GameObject;
import gameObject.GameUnit;
import grid.Coordinate;


@JsonAutoDetect
public abstract class Action {
    private String myName;
    private List<Coordinate> myAOE;
    private boolean isAround;
    private int masterIndex;

    public Action () {
        List<Coordinate> AOE = new ArrayList<>();
        AOE.add(new Coordinate(0, 1));
        setAround(false);
        masterIndex = -1;
    }

    public abstract void doAction (GameUnit initiator, GameObject receiver);

    public void setName (String name) {
        myName = name;
    }

    public abstract boolean isValidAction (GameUnit gameUnit, GameObject gameObject);

    public String getName () {
        return myName;
    }

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

    public int getMasterIndex () {
        return masterIndex;
    }

    public void setMasterIndex (int newIndex) {
        masterIndex = newIndex;
    }
}
