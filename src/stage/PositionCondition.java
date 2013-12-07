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

    public PositionCondition () {
        super();
        myData.put("x", "5");
        myData.put("y", "1");
        myData.put("team name", "player");
    }

    @Override
    boolean isFulfilled (Stage stage) {
        GameUnit object =
                (GameUnit) stage.getGrid().getObject(GridConstants.GAMEUNIT,
                                                     new Coordinate(Integer.parseInt(myData
                                                             .get("x")), Integer.parseInt(myData
                                                             .get("y"))));

        if (object != null) { return object.getAffiliation().equals(myData.get("team name")); }

        return false;
    }

    @Override
    public String toString () {
        return "Position Condition";
    }
}
