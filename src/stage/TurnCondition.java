package stage;


public class TurnCondition extends Condition {

    public TurnCondition () {
        super();
        myNeededData.add("count");
    }

    @Override
    boolean isFulfilled (Stage stage) {
        // TODO: when we have something that keeps track of the game/turns, it needs to be stored
        // and accessible from Grid
        return false;
    }

}
