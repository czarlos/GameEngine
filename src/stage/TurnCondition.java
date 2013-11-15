package stage;

import grid.Grid;

public class TurnCondition extends Condition {

    public TurnCondition () {
        super();
        neededData.add("count");
    }

    @Override
    boolean isFulfilled (Grid grid) {
        // TODO: when we have something that keeps track of the game/turns, it needs to be stored and accessible from Grid
        return false;
    }

}
