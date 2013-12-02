package stage;

import gameObject.GameUnit;
import grid.Coordinate;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

/**
 * Is fulfilled when a GameUnit of the correct team is at x, y
 * 
 * @author Leevi
 * 
 */

@JsonAutoDetect
public class PositionCondition extends Condition {

	public PositionCondition() {
		super();
		myData.put("x", "5");
		myData.put("y", "1");
		myData.put("team name", "default");
	}

	@Override
	boolean isFulfilled(Stage stage) {
		Object object = stage.getGrid().getObject(
				new Coordinate(Integer.parseInt(myData.get("x")), Integer
						.parseInt(myData.get("y"))));

		if (object instanceof GameUnit) {
			GameUnit gu = (GameUnit) object;
			return gu.getAffiliation().equals(myData.get("affiliation"));
		}

		return false;
	}

	@Override
	public String toString() {
		return "Position Condition";
	}
}
