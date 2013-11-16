package tests;

import static org.junit.Assert.*;
import java.util.ArrayList;
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

        Node node = new Node(null, new Coordinate(1, 1));
        Node otherNode = new Node(null, new Coordinate(2, 2));
        assertEquals(pathFind.isNeighbor(node, otherNode), true);
    }

    @Test
    public void testAddNeighbor () {
        PathFinding pathFind = new PathFinding();
        List<Node> nodeList = new ArrayList<Node>();

        nodeList.add(new Node(null, new Coordinate(1, 1)));
        nodeList.add(new Node(null, new Coordinate(2, 2)));
        nodeList.add(new Node(null, new Coordinate(4, 4)));
        nodeList.add(new Node(null, new Coordinate(7, 6)));
        nodeList.add(new Node(null, new Coordinate(1, 2)));
        nodeList.add(new Node(null, new Coordinate(7, 7)));

        pathFind.addNeighbors(nodeList);
        assertEquals(nodeList.get(0).getNeighbors().size(), 2, 0);
        assertEquals(nodeList.get(1).getNeighbors().size(), 2, 0);
        assertEquals(nodeList.get(2).getNeighbors().size(), 0, 0);
        assertEquals(nodeList.get(3).getNeighbors().size(), 1, 0);
        assertEquals(nodeList.get(4).getNeighbors().size(), 2, 0);
        assertEquals(nodeList.get(5).getNeighbors().size(), 1, 0);

    }

    @Test
    public void testPathFind () {

        Grid grid = new Grid(7, 7, 0);

        PathFinding pathFind = new PathFinding();

        List<Node> nodeGrid = pathFind.coordinatesToNodes(grid);
        pathFind.addNeighbors(nodeGrid);

        Node start = nodeGrid.get(0);
        Node end = nodeGrid.get(nodeGrid.size());

        List<Node> path = pathFind.aStar(start, end);
        for (Node node : path) {
            System.out.println(node);
        }

    }

}
