package stage;

import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * Can be used for both winning conditions and NOT winning conditions.
 * isFulfilled is true if the number of conditions satisfied is greater than or
 * equal to the number of conditions needed.
 * 
 * @author Leevi
 * 
 */
@JsonAutoDetect
public class WinCondition {
    @JsonProperty
    private int conditionsNeeded;

    @JsonProperty
    private ArrayList<Condition> conditions;

    public WinCondition () {
        conditions = new ArrayList<Condition>();
    }

    public void setConditionsNeeded (int i) {
        conditionsNeeded = i;
    }

    public void addCondition (Condition c) {
        conditions.add(c);
    }

    // TODO: currently no conditions = auto-win, but maybe we should make it never-win
    public boolean isFulfilled (Stage stage) {
        int count = 0;
        for (Condition c : conditions) {
            if (c.isFulfilled(stage))
                count++;
        }
        return count >= conditionsNeeded;
    }
}
