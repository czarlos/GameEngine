package stage;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonAutoDetect
public class PositionCondition extends Condition {
    // temp dummy variables
    @JsonProperty
    private int playersPositionX;
    
    // You can specify the name of the JSON key like so:
    @JsonProperty("playersPositionY")
    private int playersPositionY;
    // All JSON serializable classes either need to have an empty constructor
    public PositionCondition () {
        super();
        neededData.add("x");
        neededData.add("y");
    }

    // Or use the following notation
    @JsonCreator
    public PositionCondition (@JsonProperty("playersPositionX") int X,
                              @JsonProperty("PlayersPositionY") int Y) {
        // TODO: needs to be passed in something that allows this condition to dynamically retrieve
        // player's x and y
        playersPositionX = X;
        playersPositionY = Y;
    }

    @Override
    boolean isFulfilled () {
        boolean isX = Integer.parseInt(myData.get("x")) == playersPositionX;
        boolean isY = Integer.parseInt(myData.get("y")) == playersPositionY;
        return isX & isY;
    }
}
