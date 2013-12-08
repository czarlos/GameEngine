package utils;

import grid.Coordinate;


/**
 * Utilities class for use with game objects.
 * 
 * @author Kevin, carlosreyes
 * 
 */
public class UnitUtilities {

    /**
     * Gets the distance between two coordinates.
     */
    public static final double calculateLength (Coordinate pos1, Coordinate pos2) {
        return Math.sqrt(Math.pow(pos1.getX() - pos2.getX(), 2)
                         + Math.pow(pos1.getY() - pos2.getY(), 2));
    }

    /**
     * Gets the Manhattan distance between two coordinates
     * @param pos1 Coordinate of the first position
     * @param pos2 Coordinate of the second position
     * @return int of the Manhattan distance
     */
    public static final int calculateDistance (Coordinate pos1, Coordinate pos2) {
        return Math.abs(pos1.getX() - pos2.getX()) + Math.abs(pos1.getY() - pos2.getY());
    }
}
