package stage;

/**
 * A condition that will always return false
 * 
 * @author Leevi
 * 
 */
public class FalseCondition extends Condition {

    public FalseCondition () {
    }

    @Override
    boolean isFulfilled (Stage stage) {
        return false;
    }

    public String toString () {
        return "False Condition";
    }
}
