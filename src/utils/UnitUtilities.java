package utils;

import grid.Coordinate;


/**
 * Utilities class for use with game objects.
 * 
 * @author carlosreyes
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
}
