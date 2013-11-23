package tests;

import static org.junit.Assert.*;
import grid.Coordinate;
import grid.Grid;
import org.junit.Test;


public class GridTests {

    @Test
    public void testTiles () {
        Grid grid = new Grid(10, 10, 1);
        assertTrue(grid.isActive(new Coordinate(3, 5)));
    }

}
