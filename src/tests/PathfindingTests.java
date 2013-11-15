package tests;

import static org.junit.Assert.*;
import java.util.List;
import grid.Coordinate;
import grid.Grid;
import org.junit.Test;
import unit_ai.PathFinding;
import unit_ai.PathFinding.Node;


public class PathfindingTests {
    Grid grid;

    /**
     * Initializes grid for use in the unit tests
     */
    public void initializeGrid () {
        grid = new Grid(10, 10, 1);
    }

    @Test
    public void testCoordinateToNode () {
        initializeGrid();
        PathFinding pathFind = new PathFinding();
        List<Node> nodeList = pathFind.coordinatesToNodes(grid);
        assertEquals(nodeList.size(), 100, 0);
    }

    @Test
    public void testIsNeighbor () {

    }

}
