package stage;

import grid.Grid;


public class StatCondition extends Condition {

    public StatCondition () {
        super();
        neededData.add("statType");
        neededData.add("value");
        neededData.add("affilation");
    }

    /**
     * Returns true if a unit of a certain "affiliation" has achieved a stat of "statType" higher
     * than "value"
     */
    @Override
    boolean isFulfilled (Grid grid) {

        return false;
    }

}
