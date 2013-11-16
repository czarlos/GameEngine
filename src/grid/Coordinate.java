package grid;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * 
 * Contains an x and y for a coordinate on the grid
 * 
 * @author Kevin
 * @author Ken
 * 
 */

@JsonAutoDetect
public class Coordinate {
    private int myX;
    private int myY;

    /**
     * Creates a new coordinate with a given x and y
     * 
     * @param x - The x location of a coordinate
     * @param y - The y location of a coordinate
     */
    public Coordinate (@JsonProperty("X") int x, @JsonProperty("Y") int y) {
        myX = x;
        myY = y;
    }

    // @Override
    // public int hashCode () {
    // int hash = myX * 123456789;
    // hash = hash + myY * 3;
    // return hash;
    // }

    /**
     * @return The x location of the coordinate
     */
    public int getX () {
        return myX;
    }

    /**
     * Sets the x location of the coordinate
     * 
     * @param x - The x location to set the coordinate to
     */
    public void setX (int x) {
        myX = x;
    }

    /**
     * @return The y location of the coordinate
     */
    public int getY () {
        return myY;
    }

    /**
     * Sets the y location of the coordinate
     * 
     * @param y - The y location to set the coordinate to
     */
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
