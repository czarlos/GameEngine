package stage;

import gameObject.GameUnit;
import java.util.List;


/**
 * Returns true if a unit of a certain "affiliation" has achieved a stat of "statType" higher
 * than "value"
 * 
 * @author Leevi
 * 
 */
public class StatCondition extends Condition {

    public StatCondition () {
        super();
        myNeededData.add("statType");
        myNeededData.add("value");
        myNeededData.add("affilation");
    }

    @Override
    boolean isFulfilled (Stage stage) {
        List<GameUnit> theTeam = stage.getTeamUnits(myData.get("affiliation"));
        for (GameUnit gu : theTeam) {
            if (gu.getStat(myData.get("statType")) > Integer.parseInt(myData.get("value"))) { return true; }
        }
        return false;
    }

}
