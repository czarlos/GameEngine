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
        myCount = 3;
    }

    public void setCount (int count) {
        myCount = count;
    }

    public int getCount () {
        return myCount;
    }

    @Override
    boolean isFulfilled (Stage stage) {
        return stage.getPhaseCount() / stage.getNumberOfTeams() >= myCount;
    }

    @Override
    public String toString () {
        return "Turn Condition";
    }
}
