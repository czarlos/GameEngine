package stage;

public class FalseCondition extends Condition {

    public FalseCondition () {
    }

    @Override
    boolean isFulfilled (Stage stage) {
        return false;
    }
    
    public String toString(){
        return "False Condition";
    }
}
