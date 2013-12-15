package stage;

/**
 * A condition that can be fulfilled to determine if a team has won or not
 * 
 * @author Leevi
 * 
 */
public abstract class Condition {
    abstract boolean isFulfilled (Stage stage);
}
