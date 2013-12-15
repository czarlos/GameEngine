package tests;

import gameObject.GameUnit;
import gameObject.Item;
import gameObject.action.CombatAction;
import grid.Coordinate;
import grid.Grid;
import grid.GridConstants;
import org.junit.Test;
import controllers.WorldManager;


public class ActionTests {

    @Test
    public void test () {
        WorldManager myWorldManager = new WorldManager();
        CombatAction action = new CombatAction();
        Item item = new Item();
        GameUnit defaultUnit = new GameUnit();

        myWorldManager.addStage(15, 15, 0, "Game", 0);
        myWorldManager.place(GridConstants.GAMEUNIT, 0, new Coordinate(2, 2));
        defaultUnit = (GameUnit) ((Grid) myWorldManager.getGrid()).getArray(GridConstants.GAMEUNIT)[2][2];

        action.setName("attack");
        item.addAction("attack");
        defaultUnit.addItem(item);
    }
}
