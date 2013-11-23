package gameObject;

import grid.Coordinate;
import grid.Grid;
import java.util.List;
import java.util.Map;


/**
 * Factories produce units which are placed on the grid for the
 * user to use when they are finished.
 * 
 * @author carlosreyes
 * 
 */
public class UnitFactory extends GameObject {

    private Map<String, GameUnit> myPossibleUnits;
    private Map<String, Integer> myNumberOfTurns;
    private int myCounter = 0;
    private Map<GameUnit, Integer> myPendingUnits;
    private Grid grid;
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
        grid.placeObject(validLocation(), unit);
        unitList.add(unit);
        myCounter++;
    }
    
    /**
     * Returns a coordinate that is one spot to the right of the 
     * location of the factory.
     * @return
     */
    private Coordinate validLocation() {
        int newLocation = myLocation.getX() + 1;
        return new Coordinate(newLocation, myLocation.getY());
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
