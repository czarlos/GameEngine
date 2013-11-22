package stage;

import view.Customizable;
import com.fasterxml.jackson.annotation.JsonAutoDetect;


@JsonAutoDetect
public abstract class Condition extends Customizable {
    abstract boolean isFulfilled (Stage stage);

    public Condition () {
    }
}
