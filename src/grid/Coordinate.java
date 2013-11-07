package grid;

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
