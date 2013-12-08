package stage;

import gameObject.GameUnit;
import grid.Coordinate;
import grid.GridConstants;
import com.fasterxml.jackson.annotation.JsonAutoDetect;


/**
 * Is fulfilled when a GameUnit of the correct team is at x, y
 * 
 * @author Leevi
 * 
 */

@JsonAutoDetect
public class PositionCondition extends Condition {

    private int myX;
    private int myY;
    private String myAffiliation;

    public PositionCondition () {
        myX = 5;
        myY = 1;
        myAffiliation = "player";
    }

    public void setX (int x) {
        myX = x;
    }

    public void setY (int y) {
        myY = y;
    }

    public void setAffiliation (String affiliation) {
        myAffiliation = affiliation;
    }

    public int getX () {
        return myX;
    }

    public int getY () {
        return myY;
    }

    public String getAffiliation () {
        return myAffiliation;
    }

    @Override
    boolean isFulfilled (Stage stage) {
        GameUnit object =
                (GameUnit) stage.getGrid().getObject(GridConstants.GAMEUNIT,
                                                     new Coordinate(myX, myY));

        if (object != null) { return object.getAffiliation().equals(myAffiliation); }

        return false;
    }

    @Override
    public String toString () {
        return "Position Condition";
    }
}
