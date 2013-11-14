package tests;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;
import gameObject.GameUnit;
import grid.Coordinate;
import org.junit.Test;
import stage.Stage;

public class StageTest {

    @Test
    public void findUnitsTest () {
        
        List<List<GameUnit>> fullUnitList = new ArrayList<List<GameUnit>>();
        
        Stage stage = new Stage();
        GameUnit unit = new GameUnit();
        Coordinate coord = new Coordinate(0, 0);
        unit.setGridPosition(coord);
        List<GameUnit> aiList = new ArrayList<GameUnit>();
        aiList.add(unit);
        
        GameUnit unit1 = new GameUnit();
        GameUnit unit2 = new GameUnit();
        Coordinate coord1 = new Coordinate(12, 12);
        Coordinate coord2 = new Coordinate(4, 4);
        unit1.setGridPosition(coord1);
        unit2.setGridPosition(coord2);
        List<GameUnit> unitList = new ArrayList<GameUnit>();
        unitList.add(unit1);
        unitList.add(unit2);
        
        fullUnitList.add(unitList);
        fullUnitList.add(aiList);
        stage.setTeamUnitList(fullUnitList);

        stage.doAIMove(1, 0);
        assertEquals(unit.getGridPosition(), unit2.getGridPosition());
    }

}
