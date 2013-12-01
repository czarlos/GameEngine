package gameObject;

import grid.Coordinate;
import grid.Grid;
import java.util.List;
import java.util.Map;
import team.Team;

/**
 * Factories produce units which are placed on the grid for the user to use when
 * they are finished. Factories place units to the side of the factory, only one
 * unit can be in this place at a time.
 * 
 * @author carlosreyes
 * 
 */
public class UnitFactory extends GameObject {

	private Map<String, GameUnit> myPossibleUnits;
	private Map<String, Integer> myNumberOfTurns;
	private Map<GameUnit, Integer> myPendingUnits;
	private Grid myGrid;
	private Coordinate myLocation;

	public UnitFactory(Map<String, GameUnit> possibleUnits, Grid grid,
			Coordinate location) {
		myPossibleUnits = possibleUnits;
		myLocation = location;
	}

	/**
	 * Checks through all units adjacent to the factory, if a coordinate is a
	 * valid place for a unit to be, the factory produces the unit on this
	 * coordinate.
	 * 
	 * @return
	 */
	private Coordinate validLocation(GameUnit unit) {

		List<Coordinate> coords = myGrid.adjacentCoordinates(myLocation);
		for (Coordinate c : coords) {
			if (myGrid.getTile(c).isPassable(unit)) {
				return c;
			}
		}
		return myLocation;
	}

	/**
	 * Loops through all of the units pending creation, if the amount of turns
	 * that it takes for that unit to be made has been met, this unit is put in
	 * the unitList of the team that is responsible for creating said unit.
	 * Units are placed at a valid tile adjacent to the factory. If a unit is
	 * not being created the turn counter on that unit is decremented. Should be
	 * called every turn.
	 * 
	 * @param team
	 *            - The team associated with this factory
	 */
	public void makeUnit(Team team) {
		for (GameUnit unit : myPendingUnits.keySet()) {
			if (myPendingUnits.get(unit) == 0) {
				myPendingUnits.remove(unit);
				unit.setAffiliation(team.getName());
				myGrid.placeObject(validLocation(unit), unit);
			} else {
				myPendingUnits.put(unit, myPendingUnits.get(unit) - 1);
			}
		}
	}

	/**
	 * Adds the desired unit to be created to the map of the units pending
	 * creation along with the number of turns it takes to make this unit.
	 * 
	 * @param unitName
	 *            - The string name of the unit to be created
	 */
	public void enqueueUnit(String unitName) {
		myPendingUnits.put(myPossibleUnits.get(unitName),
				myNumberOfTurns.get(unitName));
	}
}
