package stage;

import grid.Grid;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;


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
    public boolean hasWon (Grid grid) {
        int count = 0;
        for (Condition c : conditions) {
            if (c.isFulfilled(grid))
                count++;
        }
        return count == conditionsNeeded;
    }
}
