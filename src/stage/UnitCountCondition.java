package stage;

import grid.Grid;


public class UnitCountCondition extends Condition {

    public UnitCountCondition () {
        super();
        neededData.add("count");
        neededData.add("affiliation");
    }

    @Override
    boolean isFulfilled (Grid grid) {
        // TODO: when unit/grid interaction refactoring is done, count units of a certain
        // affiliation
        return false;
    }

}
