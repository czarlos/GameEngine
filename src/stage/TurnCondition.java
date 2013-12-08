package stage;

/**
 * Is fulfilled when a specified number of turns has passed
 * 
 * @author Leevi
 * 
 */
public class TurnCondition extends Condition {
    private int myCount;

    public TurnCondition () {
        myCount = 5;
    }

    public void setCount (int count) {
        myCount = count;
    }

    public int getCount () {
        return myCount;
    }

    @Override
    boolean isFulfilled (Stage stage) {
        // TODO: when we have something that keeps track of the game/turns, it
        // needs to be stored
        // and accessible from Stage
        return false;
    }

    @Override
    public String toString () {
        return "Turn Condition";
    }

}
