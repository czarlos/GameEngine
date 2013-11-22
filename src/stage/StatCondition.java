package stage;


public class StatCondition extends Condition {

    public StatCondition () {
        super();
        myNeededData.add("statType");
        myNeededData.add("value");
        myNeededData.add("affilation");
    }

    /**
     * Returns true if a unit of a certain "affiliation" has achieved a stat of "statType" higher
     * than "value"
     */
    @Override
    boolean isFulfilled (Stage stage) {

        return false;
    }

}
