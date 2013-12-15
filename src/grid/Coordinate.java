package grid;

/**
 * Coordinate class, holds an x and y
 * 
 * @author Kevin, Ken
 * 
 */
public class Coordinate {
    private int myX;
    private int myY;

    public Coordinate (int x, int y) {
        myX = x;
        myY = y;
    }

    public int getX () {
        return myX;
    }

    public int getY () {
        return myY;
    }
}
