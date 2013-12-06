package tests;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;
import gameObject.GameUnit;
import grid.Coordinate;
import grid.Grid;
import grid.Tile;
import org.junit.Test;
import unit_ai.PathFinding;


public class PathfindingTests {
    Grid grid;

    /**
     * Initializes grid for use in the unit tests
     */
    public void initializeGrid () {
        grid = new Grid(10, 10, 1);
    }

    @Test
    public void testCoordinateToTile () {
        initializeGrid();
        GameUnit unit = new GameUnit();
        List<Tile> tileList = PathFinding.coordinatesToTiles(grid, unit);
        assertEquals(tileList.size(), 100, 0);
    }

    @Test
    public void testIsNeighbor () {
        initializeGrid();
        Tile tile = new Tile(null, new Coordinate(1, 1));
        Tile otherTile = new Tile(null, new Coordinate(2, 1));
        assertEquals(PathFinding.isNeighbor(tile, otherTile, grid), true);
    }

    @Test
    public void testAddNeighbor () {
        List<Tile> tileList = new ArrayList<Tile>();

        tileList.add(new Tile(null, new Coordinate(1, 1)));
        tileList.add(new Tile(null, new Coordinate(2, 2)));
        tileList.add(new Tile(null, new Coordinate(4, 4)));
        tileList.add(new Tile(null, new Coordinate(7, 6)));
        tileList.add(new Tile(null, new Coordinate(1, 2)));
        tileList.add(new Tile(null, new Coordinate(7, 7)));

        PathFinding.addNeighbors(tileList, grid);
        assertEquals(tileList.get(0).getNeighbors().size(), 2, 0);
        assertEquals(tileList.get(1).getNeighbors().size(), 2, 0);
        assertEquals(tileList.get(2).getNeighbors().size(), 0, 0);
        assertEquals(tileList.get(3).getNeighbors().size(), 1, 0);
        assertEquals(tileList.get(4).getNeighbors().size(), 2, 0);
        assertEquals(tileList.get(5).getNeighbors().size(), 1, 0);

    }

    @Test
    public void testPathFind () {

        Grid grid = new Grid(6, 6, 0);

        GameUnit unit = new GameUnit();

        List<Tile> tileGrid = PathFinding.coordinatesToTiles(grid, unit);
        PathFinding.addNeighbors(tileGrid, grid);

        Tile start = tileGrid.get(0);
        Tile end = tileGrid.get(tileGrid.size() - 1);
        List<Tile> path = PathFinding.findPath(start, end, grid);
        for (Tile tile : path) {
            System.out.println(tile.getCoordinate().getX() + " "
                               + tile.getCoordinate().getY());
        }

    }

    @Test
    public void testAutoMove () {
        Grid grid = new Grid(6, 6, 0);

        GameUnit unit = new GameUnit();

        List<Tile> tileGrid = PathFinding.coordinatesToTiles(grid, unit);
        PathFinding.addNeighbors(tileGrid, grid);

        Tile start = tileGrid.get(0);
        Tile end = tileGrid.get(tileGrid.size() - 1);
        // List<Tile> path = PathFinding.findPath(start, end);

        PathFinding.autoMove(start, end, unit, grid);
    }

}
