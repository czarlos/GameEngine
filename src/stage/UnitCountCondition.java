package stage;

import gameObject.GameUnit;
import java.util.List;


/**
 * Is fulfilled when the number of units of a certain affiliation are greater
 * than or less than a certain amount
 * 
 * @author Leevi
 * 
 */
public class UnitCountCondition extends Condition {

    public UnitCountCondition () {
        super();
        myData.put("count", "0");
        myData.put("affiliation", "enemy");
        myData.put("greater?", "false");
    }

    @Override
    boolean isFulfilled (Stage stage) {
        List<GameUnit> theTeam = stage.getTeamUnits(myData.get("affiliation"));
        if (Boolean.parseBoolean(myData.get("boolean"))) {
            return Integer.parseInt(myData.get("count")) < theTeam.size();
        }
        else {
            return Integer.parseInt(myData.get("count")) >= theTeam.size();
        }
    }

    @Override
    public String toString () {
        return "Unit Count Condition";
    }
}
