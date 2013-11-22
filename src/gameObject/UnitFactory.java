package gameObject;

import grid.Coordinate;
import java.util.List;
import java.util.Map;

/**
 * Factories produce units which are placed on the grid for the
 * user to use when they are finished.
 * @author carlosreyes
 *
 */
public class UnitFactory extends GameUnit {
    
    private Map<String, GameUnit> myPossibleUnits;
    private Map<String, Integer> myNumberOfTurns;
    private int myAffiliation;
    private int myCounter = 0;
    private Map<GameUnit, Integer> myPendingUnits;
    
    public UnitFactory(Coordinate gridPosition, Map<String, GameUnit> possibleUnits) {
        super.myGridPosition = gridPosition;
        myPossibleUnits = possibleUnits;
    }
    
    /**
     * Makes new unit from the possible units and puts it into your list of units,
     * gives the unit a generic name + a number to distinguish it from other similar units.
     * Makes units and puts them at the same grid position as the factory.
     * @param unitName
     * @param teamUnitList
     */
    public void produce (String unitName, List<List<GameUnit>> teamUnitList) {
        GameUnit unit = myPossibleUnits.get(unitName);
        unit.setName(unitName + myCounter);
        unit.setGridPosition(new Coordinate(getGridPosition().getX(), getGridPosition().getY()));
        teamUnitList.get(myAffiliation).add(unit);
        myCounter++;
    }
    
    public void makeUnit (String unitName, List<List<GameUnit>> teamUnitList) {
        for (GameUnit unit : myPendingUnits.keySet()) {
            if (myPendingUnits.get(unit) == 0) {
                produce(unitName, teamUnitList);
            }
        }
    }
}
