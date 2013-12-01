package unit_ai;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import utils.UnitUtilities;
import gameObject.GameUnit;
import grid.Coordinate;
import grid.Grid;
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
	 *            - The tile at which the move unit is at
	 * @param end
	 *            - The tile at which the target unit is at
	 * @param unit
	 *            - The unit being moved.
	 */
	public static void autoMove(Tile start, Tile end, GameUnit unit, Grid grid) {
		int range = unit.getStat("movement");
		List<Tile> path = findPath(start, end);
		Tile newTile = path.get(range);
		grid.doMove(grid.getUnitCoordinate(unit), newTile.getCoordinate());
	}

	/**
	 * tileGrid must be made from the grid and all of its neighbors must be put
	 * in place, start and end must be in the list tileGrid. A BFS was used and
	 * the paths formed are contructed by storing "pointers" to the parents of
	 * each tile visited, when the end tile is found, it simply follows the
	 * pointers back to the start tile to determine the path.
	 * 
	 * @param start
	 *            - The tile at which the move unit is at
	 * @param end
	 *            - The tile at which the target unit is at
	 * @return
	 */
	public static List<Tile> findPath(Tile start, Tile end) {
		Queue<Tile> queue = new LinkedList<Tile>();
		List<Tile> visited = new ArrayList<Tile>();
		List<Tile> path = new ArrayList<Tile>();

		queue.add(start);

		while (!queue.isEmpty()) {
			Tile workingTile = queue.poll();
			if (workingTile.equals(end)) {
				while (!workingTile.equals(start)) {
					path.add(workingTile);
					workingTile = workingTile.getParent();
				}
				return path;
			} else {
				if (!visited.contains(workingTile)) {
					visited.add(workingTile);
					for (Tile neighbor : workingTile.getNeighbors()) {
						if (!visited.contains(neighbor)) {
							neighbor.setParent(workingTile);
							queue.add(neighbor);
						}
					}
				}
			}
		}
		return path;

	}

	/**
	 * Makes a list of all tiles on the graph from the grid. This list of tiles
	 * does not contain neighbor data, and gets sent to the addNeighbors method
	 * 
	 * @param grid
	 *            - The grid in use
	 * @param unit
	 *            - The unit being moved
	 * @return
	 */
	public static List<Tile> coordinatesToTiles(Grid grid, GameUnit unit) {
		List<Tile> tileList = new ArrayList<Tile>();
		for (int i = 0; i < grid.getTiles().length; i++) {
			for (int j = 0; j < grid.getTiles().length; j++) {
				if (grid.getTile(new Coordinate(i, j)).isPassable(unit))
					tileList.add(new Tile(null, new Coordinate(i, j)));
			}
		}
		return tileList;
	}

	/**
	 * Adds a list of neighboring tiles to every tile in the list of tiles in a
	 * grid.
	 * 
	 * @param tileList
	 *            - The list of tiles in the grid
	 */
	public static void addNeighbors(List<Tile> tileList) {
		for (Tile tile : tileList) {
			List<Tile> tileAdjacencyList = new ArrayList<>();
			for (Tile otherTile : tileList) {
				if (isNeighbor(tile, otherTile) && !otherTile.equals(tile)) {
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
	 *            - A tile
	 * @param otherTile
	 *            - A different tile
	 * @return
	 */
	public static boolean isNeighbor(Tile tile, Tile otherTile) {
		double delta = UnitUtilities.calculateLength(tile.getCoordinate(),
				otherTile.getCoordinate());
		if (delta <= Math.sqrt(2)) {
			return true;
		}
		return false;
	}
}
