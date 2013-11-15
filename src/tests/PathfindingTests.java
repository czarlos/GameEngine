package tests;

import static org.junit.Assert.*;
import java.util.List;
import grid.Coordinate;
import grid.Grid;
import org.junit.Test;
import unit_ai.PathFinding;
import unit_ai.Node;

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
        PathFinding pathFind = new PathFinding();

        Node node = new Node(null, new Coordinate(1,1));
        Node otherNode = new Node(null, new Coordinate(2,2));
        assertEquals(pathFind.isNeighbor(node, otherNode), true);
    }

}
