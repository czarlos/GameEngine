package view.canvas;

import grid.Coordinate;

/**
 * Interface to handle events when the grid is clicked.
 *
 */

public interface GridMouseListener {
    
    /**
     * Method to be called on subscriber when grid is clicked
     * @param c Coordinate clicked on grid
     */
    public abstract void gridClicked (Coordinate c);
}
