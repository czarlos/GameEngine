package stage;

import gameObject.GameUnit;
import java.util.List;


/**
 * Is fulfilled when the number of units of a certain affiliation are greater than or less than a
 * certain amount
 * 
 * @author Leevi
 * 
 */
public class UnitCountCondition extends Condition {

    public UnitCountCondition () {
        super();
        myNeededData.add("count");
        myNeededData.add("affiliation");
        myNeededData.add("greater?");
    }

    @Override
    boolean isFulfilled (Stage stage) {
        List<GameUnit> theTeam = stage.getTeamUnits(myData.get("affiliation"));
        if (Boolean.parseBoolean(myData.get("boolean"))) {
            return Integer.parseInt("count") < theTeam.size();
        }
        else {
            return Integer.parseInt("count") > theTeam.size();
        }
    }
}
