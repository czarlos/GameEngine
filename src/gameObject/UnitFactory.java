package gameObject;

import grid.Coordinate;
import grid.Grid;
import java.util.List;
import java.util.Map;


/**
 * Factories produce units which are placed on the grid for the
 * user to use when they are finished. Factories place units to
 * the side of the factory, only one unit can be in this place
 * at a time. When a user is on this tile it will lock the factory.
 * 
 * @author carlosreyes
 * 
 */
public class UnitFactory extends GameObject {

    private Map<String, GameUnit> myPossibleUnits;
    private Map<String, Integer> myNumberOfTurns;
    private int myCounter = 0;
    private Map<GameUnit, Integer> myPendingUnits;
    private Grid myGrid;
    private Coordinate myLocation;

    public UnitFactory (Map<String, GameUnit> possibleUnits, Grid grid, Coordinate location) {
        myPossibleUnits = possibleUnits;
        myLocation = location;
    }

    /**
     * Makes new unit from the possible units and puts it into your list of units,
     * gives the unit a generic name + a number to distinguish it from other similar units.
     * Makes units and puts them at the same grid position as the factory.
     * 
     * @param unitName
     * @param teamUnitList
     */
    public void produce (String unitName, List<GameUnit> unitList) {
        GameUnit unit = myPossibleUnits.get(unitName);
        unit.setName(unitName + myCounter);
        myGrid.placeObject(validLocation(unit), unit);
        unitList.add(unit);
        myCounter++;
    }
    
    /**
     * Checks through all units adjacent to the factory,
     * if a coordinate is a valid place for a unit to be,
     * the factory produces the unit on this coordinate.
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
     * Checks through the pending units list to see if any units have finished completing
     * if they have, calls produce to make the unit that has finished.
     * @param unitName - The unique name of the unit being made
     * @param teamUnitList - The place where units will be placed
     */
    public void makeUnit (String unitName, List<GameUnit> unitList) {
        for (GameUnit unit : myPendingUnits.keySet()) {
            if (myPendingUnits.get(unit) == 0) {
                produce(unitName, unitList);
            }
        }
    }
}
