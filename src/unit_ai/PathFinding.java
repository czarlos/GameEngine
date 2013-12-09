package unit_ai;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import utils.UnitUtilities;
import gameObject.GameUnit;
import grid.Coordinate;
import grid.Grid;
import grid.GridConstants;
import grid.Tile;


/**
 * This class is a utilities class for AI motion, it contains a method that can
 * find a shortest valid path between two objects as well as a method that uses
 * this class to seek out the closest unit.
 * 
 * @author carlosreyes
 * 
 */
public class PathFinding {
    /**
     * AutoMove moves a unit the appropriate amount of units forward (the max
     * number possible based on that unit's movement stats) whenever it is
     * called. It does so by finding the shortest distance from the unit to the
     * target and moving as close to that target as possible. Example usage:
     * Whenever it is the AI's turn, if it can't attack, move to closest target.
     * 
     * @param start
     *        - The tile at which the move unit is at
     * @param end
     *        - The tile at which the target unit is at
     * @param unit
     *        - The unit being moved.
     */
    public static void autoMove (Tile start, Tile end, GameUnit unit, Grid grid) {
        int range = unit.getStat("movement");
        List<Tile> path = findPath(start, end, grid);

        Tile newTile;
        if (range > path.size()) {
            newTile = path.get(path.size() - 1);
        }
        else {
            newTile = path.get(path.size() - (range - 1)/start.getMoveCost());
        }

        for (Tile t : path) {
            t.setActive(true);
        }

        grid.doMove(grid.getObjectCoordinate(GridConstants.GAMEUNIT, unit),
                    grid.getObjectCoordinate(GridConstants.TILE, newTile));
    }

    /**
     * tileGrid must be made from the grid and all of its neighbors must be put
     * in place, start and end must be in the list tileGrid. A BFS was used and
     * the paths formed are contructed by storing "pointers" to the parents of
     * each tile visited, when the end tile is found, it simply follows the
     * pointers back to the start tile to determine the path.
     * 
     * @param start
     *        - The tile at which the move unit is at
     * @param end
     *        - The tile at which the target unit is at
     * @return
     */
    public static List<Tile> findPath (Tile start, Tile end, Grid grid) {
        Queue<Tile> queue = new LinkedList<Tile>();
        Set<Tile> visited = new HashSet<Tile>();
        List<Tile> path = new ArrayList<Tile>();
        Map<Tile, Integer> weights = new HashMap<Tile, Integer>();

        for (int i = 0; i < grid.getWidth(); i++) {
            for (int j = 0; j < grid.getHeight(); j++) {
                weights.put((Tile) grid.getObject(GridConstants.TILE, new Coordinate(i, j)),
                            Integer.MAX_VALUE);
            }
        }

        queue.add(start);
        weights.put(start, 0);

        while (!queue.isEmpty()) {
            Tile workingTile = queue.poll();

            for (Tile neighbor : workingTile.getNeighbors()) {
                if (weights.get(workingTile) + 1 < weights.get(neighbor)) {
                    weights.put(neighbor, weights.get(neighbor) + 1);
                    neighbor.setParent(workingTile);
                }
            }

            for (Tile neighbor : workingTile.getNeighbors()) {

                if (!visited.contains(neighbor)) {
                    queue.add(neighbor);
                }
            }

            if (workingTile.equals(end)) {
                while (!workingTile.equals(start)) {
                    path.add(workingTile);
                    workingTile = workingTile.getParent();
                }
                return path;
            }

            visited.add(workingTile);
        }
        return path;

    }

    /**
     * Makes a list of all tiles on the graph from the grid. This list of tiles
     * does not contain neighbor data, and gets sent to the addNeighbors method
     * 
     * @param grid
     *        - The grid in use
     * @param unit
     *        - The unit being moved
     * @return
     */
    public static List<Tile> coordinatesToTiles (Grid grid, GameUnit unit) {
        List<Tile> tileList = new ArrayList<Tile>();
        for (int i = 0; i < grid.getTiles().length; i++) {
            for (int j = 0; j < grid.getTiles().length; j++) {
                tileList.add((Tile) grid.getObject(GridConstants.TILE, new Coordinate(i, j)));
            }
        }
        return tileList;
    }

    /**
     * Adds a list of neighboring tiles to every tile in the list of tiles in a
     * grid.
     * 
     * @param tileList
     *        - The list of tiles in the grid
     */
    public static void addNeighbors (List<Tile> tileList, Grid grid) {
        for (Tile tile : tileList) {
            List<Tile> tileAdjacencyList = new ArrayList<>();
            for (Tile otherTile : tileList) {
                if (isNeighbor(tile, otherTile, grid) && !otherTile.equals(tile)) {
                    tileAdjacencyList.add(otherTile);
                }
            }
            tile.setNeighbors(tileAdjacencyList);
        }

    }

    /**
     * Determines whether or not a tile is a neighbor of another tile. Calls a
     * utility function calculate length which calculates the distance (delta)
     * between two coordinates. If this distance is less than the square root of
     * two we know that one tile is next to another. Note: This way counts
     * diagonally positioned tiles as "next to" if this is not diagonal tiles
     * are not desired, simply change Math.sqrt(2) to 1.
     * 
     * @param tile
     *        - A tile
     * @param otherTile
     *        - A different tile
     * @return
     */
    public static boolean isNeighbor (Tile tile, Tile otherTile, Grid grid) {
        double delta =
                UnitUtilities.calculateLength(grid.getObjectCoordinate(GridConstants.TILE, tile),
                                              grid.getObjectCoordinate(GridConstants.TILE,
                                                                       otherTile));
        if (delta <= 1) { return true; }
        return false;
    }
}
