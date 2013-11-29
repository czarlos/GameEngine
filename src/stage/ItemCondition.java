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
        myData.put("affiliation", "default");
        myData.put("item", "milk");
    }

    @Override
    boolean isFulfilled (Stage stage) {
        List<GameUnit> theTeam = stage.getTeamUnits(myData.get("affiliation"));
        for (GameUnit gu : theTeam) {
            if (gu.getItemAmount(myData.get("item")) > 0) { return true; }
        }
        return false;
    }

    @Override
    public String toString () {
        return "Item Condition";
    }
}
