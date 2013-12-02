package stage;

/**
 * Is fulfilled when a specified number of turns has passed
 * 
 * @author Leevi
 * 
 */
public class TurnCondition extends Condition {

	public TurnCondition() {
		super();
		myData.put("count", "5");
	}

	@Override
	boolean isFulfilled(Stage stage) {
		// TODO: when we have something that keeps track of the game/turns, it
		// needs to be stored
		// and accessible from Stage
		return false;
	}

	@Override
	public String toString() {
		return "Turn Condition";
	}

}
