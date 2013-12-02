package gameObject;

import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonAutoDetect
public class StatModifier {
	@JsonProperty
	private Map<String, Integer> myStatModifiers;

	public StatModifier() {
	}

	public StatModifier(Map<String, Integer> stats) {
		myStatModifiers = stats;
	}

	public Map<String, Integer> getStatModifierMap() {
		return myStatModifiers;
	}

	public Integer getStatModifier(String statName) {
		return myStatModifiers.get(statName);
	}

	public void setStatModifier(String statName, Integer value) {
		myStatModifiers.put(statName, value);
	}

	public void makeStatModifier(String name, Integer value) {
		myStatModifiers.put(name, value);
	}

}
