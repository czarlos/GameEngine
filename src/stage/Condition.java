package stage;

import com.fasterxml.jackson.annotation.JsonAutoDetect;


@JsonAutoDetect
public abstract class Condition {
    abstract boolean isFulfilled (Stage stage);
}
