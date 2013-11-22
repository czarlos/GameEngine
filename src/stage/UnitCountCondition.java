package stage;


public class UnitCountCondition extends Condition {

    public UnitCountCondition () {
        super();
        myNeededData.add("count");
        myNeededData.add("affiliation");
    }

    @Override
    boolean isFulfilled (Stage stage) {
        // TODO: when unit/grid interaction refactoring is done, count units of a certain
        // affiliation
        return false;
    }

}
