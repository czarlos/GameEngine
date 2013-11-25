package tests;

import static org.junit.Assert.*;
import gameObject.GameUnit;
import org.junit.Test;
import controllers.WorldManager;
import grid.Grid;


public class StatsTest {
    private static final double DELTA = 0.001;

    @Test
    public void addStatTest () {
        WorldManager myWorldManager = new WorldManager();
        myWorldManager.addStage(15, 15, 0, "Game");
        myWorldManager.placeUnit(0, 2, 2);
        myWorldManager.addStat("Test Stat 1", 10);
        myWorldManager.addStat("Test Stat 2", 20);
        myWorldManager.addStat("Test Stat 3", 30);

        GameUnit defaultUnit = ((Grid) myWorldManager.getGrid()).getGameUnits()[2][2];
        for (int i = 1; i < 4; i++) {
            assertTrue(defaultUnit.getStats().getStatNames().contains("Test Stat " + i));
            assertEquals(i * 10, defaultUnit.getStats().getStatValue("Test Stat " + i), DELTA);
            assertEquals(i * 10, myWorldManager.getStatValue("Test Stat " + i), DELTA);
        }
    }

    @Test
    public void removeStatTest () {
        WorldManager myWorldManager = new WorldManager();
        myWorldManager.addStage(15, 15, 0, "Game");
        myWorldManager.placeUnit(0, 2, 2);
        myWorldManager.addStat("Test Stat 1", 10);
        myWorldManager.addStat("Test Stat 2", 20);
        myWorldManager.addStat("Test Stat 3", 30);
        myWorldManager.removeStat("Test Stat 1");

        GameUnit defaultUnit = ((Grid) myWorldManager.getGrid()).getGameUnits()[2][2];
        assertFalse(defaultUnit.getStats().getStatNames().contains("Test Stat 1"));

        for (int i = 2; i < 4; i++) {
            assertTrue(defaultUnit.getStats().getStatNames().contains("Test Stat " + i));
        }
    }

    @Test
    public void modStatTest () {
        WorldManager myWorldManager = new WorldManager();
        myWorldManager.addStage(15, 15, 0, "Game");
        myWorldManager.placeUnit(0, 2, 2);
        myWorldManager.addStat("Test Stat 1", 10);
        myWorldManager.addStat("Test Stat 2", 20);
        myWorldManager.addStat("Test Stat 3", 30);
        myWorldManager.modifyStat("Test Stat 1", 20);
        myWorldManager.placeUnit(0, 3, 3);

        GameUnit defaultUnit1 = ((Grid) myWorldManager.getGrid()).getGameUnits()[2][2];
        GameUnit defaultUnit2 = ((Grid) myWorldManager.getGrid()).getGameUnits()[3][3];

        for (int i = 1; i < 3; i++) {
            assertEquals(20, myWorldManager.getStatValue("Test Stat " + i), DELTA);
            // Will fail for now
            assertEquals(20, defaultUnit2.getStats().getStatValue("Test Stat " + i), DELTA);
        }

        assertEquals(30, myWorldManager.getStatValue("Test Stat 3"), DELTA);
        // Will fail for now
        assertEquals(30, defaultUnit2.getStats().getStatValue("Test Stat 3"), DELTA);

        for (int i = 1; i < 4; i++) {
            assertEquals(i * 10, defaultUnit1.getStats().getStatValue("Test Stat " + i), DELTA);
        }
    }
}
