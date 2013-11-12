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

    @Override
    public int hashCode () {
        final int prime = 31;
        int result = 1;
        result = prime * result + myX;
        result = prime * result + myY;
        return result;
    }

    @Override
    public boolean equals (Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        Coordinate other = (Coordinate) obj;
        if (myX != other.myX) return false;
        if (myY != other.myY) return false;
        return true;
    }

}
