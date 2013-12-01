package stage;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Can be used for both winning conditions and NOT winning conditions.
 * isFulfilled is true if the number of conditions satisfied is greater than or
 * equal to the number of conditions needed.
 * 
 * @author Leevi
 * 
 */
@JsonAutoDetect
public class WinCondition {
	@JsonProperty
	private int conditionsNeeded;

	@JsonProperty
	private List<Condition> myConditions;

	public WinCondition() {
		myConditions = new ArrayList<Condition>();
		conditionsNeeded = 1;
	}

	public void setConditionsNeeded(int i) {
		conditionsNeeded = i;
	}

	public int getConditionsNeeded() {
		return conditionsNeeded;
	}

	public void addCondition(Condition c) {
		myConditions.add(c);
	}

	public List<Condition> getConditions() {
		return myConditions;
	}

	public void setConditions(List<Condition> conditions) {
		myConditions = conditions;
	}

	// TODO: currently no conditions = auto-win, but maybe we should make it
	// never-win
	public boolean isFulfilled(Stage stage) {
		int count = 0;
		for (Condition c : myConditions) {
			if (c.isFulfilled(stage))
				count++;
		}
		return count >= conditionsNeeded;
	}

	public String toString() {
		return "Win Condition";
	}
}
