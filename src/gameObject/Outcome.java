package gameObject;

public abstract class Outcome {
	protected String myType;
	protected String myName;
	protected int myAmount;

	public Outcome(String type, String name, int amount) {
		myType = type;
		myName = name;
		myAmount = amount;
	}

	/**
	 * applyOutcomes edits a GameUnit based on user specified type, name, and
	 * amount. These outcomes are affected by stat differences between units
	 * (effectiveness).
	 * 
	 * @param unit
	 *            - GameUnit where values are being edited
	 * @param effectiveness
	 *            - A measurement of how much of an outcome should occur
	 */
	public abstract void applyOutcome(GameUnit unit, double effectiveness);
}
