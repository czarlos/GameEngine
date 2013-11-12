package grid;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonAutoDetect
public class Coordinate {
    private int myX;
    private int myY;

    public Coordinate (@JsonProperty("X") int x,@JsonProperty("Y") int y) {
        myX = x;
        myY = y;
    }

    public int getX () {
        return myX;
    }

    public void setX (int x) {
        myX = x;
    }

    public int getY () {
        return myY;
    }

    public void setY (int y) {
        myY = y;
    }

}
