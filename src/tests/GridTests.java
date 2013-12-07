package tests;

import static org.junit.Assert.*;
import grid.Coordinate;
import grid.Grid;
import org.junit.Test;


public class GridTests {
    private static final double DELTA = 0.001;

    @Test
    public void testTiles () {
        Grid grid = new Grid(10, 10, 1);
        //assertTrue(grid.isActive(new Coordinate(3, 5)));
    }

    @Test
    public void testGrid () {
        Grid grassGrid = new Grid(10, 10, 0);
        Grid waterGrid = new Grid(6, 6, 1);

        assertEquals(10, grassGrid.getWidth(), DELTA);
        assertEquals(10, grassGrid.getHeight(), DELTA);
        assertEquals(6, waterGrid.getWidth(), DELTA);
        assertEquals(6, waterGrid.getHeight(), DELTA);

        for (int i = 0; i < grassGrid.getWidth(); i++) {
            for (int j = 0; j < grassGrid.getHeight(); j++) {
                // assertEquals(i + ", " + j, 1, grassGrid.getTile(new
                // Coordinate(i,
                // j)).getMoveCost(), DELTA);
            }
        }

        for (int i = 0; i < waterGrid.getWidth(); i++) {
            for (int j = 0; j < waterGrid.getHeight(); j++) {
                // assertEquals(i + ", " + j, 2, waterGrid.getTile(new
                // Coordinate(i,
                // j)).getMoveCost(), DELTA);
            }
        }
    }

}
