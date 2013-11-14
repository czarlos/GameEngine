package stage;

import grid.Grid;


public class ItemCondition extends Condition {

    public ItemCondition () {
        super();
        neededData.add("item");
        // neededData.add("affiliation");
    }

    @Override
    boolean isFulfilled (Grid grid) {
        // TODO: (after unit refactoring) check if a unit is holding something with itemname that
        // matches item
        return false;
    }
}
