package stage;


public class ItemCondition extends Condition {

    public ItemCondition () {
        super();
        myNeededData.add("item");
        // neededData.add("affiliation");
    }

    @Override
    boolean isFulfilled (Stage stage) {
        // TODO: (after unit refactoring) check if a unit is holding something with itemname that
        // matches item
        return false;
    }
}
