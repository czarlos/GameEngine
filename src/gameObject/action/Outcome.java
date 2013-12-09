package gameObject.action;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import gameObject.GameUnit;


@JsonAutoDetect
public abstract class Outcome {
    protected int myAmount;
    protected boolean isFixed;
    protected String myType;

    public Outcome () {
    }

    /**
     * applyOutcomes edits a GameUnit based on user specified type, name, and
     * amount. These outcomes are affected by stat differences between units
     * (effectiveness).
     * 
     * @param unit GameUnit where values are being edited
     * @param effectiveness A measurement of how much of an outcome should occur
     */
    public abstract void applyOutcome (GameUnit unit, double effectiveness);

    /**
     * Checks whether or not an outcome is legal
     * 
     * @param unit
     * @param effectiveness
     * @return true if legal, false otherwise
     */
    public abstract boolean checkValidOutcome (GameUnit unit, double effectiveness);

    public int getAmount () {
        return myAmount;
    }

    public void setAmount (int amount) {
        myAmount = amount;
    }

    @JsonProperty("fixed")
    public boolean isFixed () {
        return isFixed;
    }

    @JsonProperty("fixed")
    public void setIsFixed (boolean fixed) {
        isFixed = fixed;
    }

    public String getType () {
        return myType;
    }

    public abstract Object getAffectee ();

    public abstract void setAffectee (Object o);

}
