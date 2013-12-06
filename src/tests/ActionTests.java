package tests;

import static org.junit.Assert.*;
import gameObject.GameObject;
import gameObject.GameUnit;
import gameObject.action.Action;
import gameObject.action.CombatAction;
import gameObject.action.MoveAction;
import gameObject.item.Item;
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

        myWorldManager.addStage(15, 15, 0, "Game");
        myWorldManager.place(GridConstants.GAMEUNIT, 0, 2, 2);
        defaultUnit = ((Grid) myWorldManager.getGrid()).getGameUnits()[2][2];

        action.setName("attack");
        item.addAction("attack");
        defaultUnit.addItem(item);
    }
}
