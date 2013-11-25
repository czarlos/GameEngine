package stage;

import gameObject.GameUnit;
import java.util.List;


/**
 * Is fulfilled when somebody from a specified team has a certain item.
 * 
 * @author Leevi
 * 
 */
public class ItemCondition extends Condition {

    public ItemCondition () {
        super();
        myNeededData.add("team");
        myNeededData.add("item");
    }

    @Override
    boolean isFulfilled (Stage stage) {
        List<GameUnit> theTeam = stage.getTeamUnits(Integer.parseInt(myData.get("team")));
        for (GameUnit gu : theTeam) {
            if (gu.getItemAmount(myData.get("item")) > 0) { return true; }
        }
        return false;
    }
}
