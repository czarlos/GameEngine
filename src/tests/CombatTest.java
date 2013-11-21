package tests;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import gameObject.CombatAction;
import gameObject.GameUnit;
import gameObject.Stat;
import gameObject.StatModifier;
import gameObject.item.Item;
import gameObject.item.Weapon;
import grid.GridConstants;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


public class CombatTest {
    GameUnit playerUnit;
    GameUnit enemyUnit;

    @BeforeClass
    public static void setUpBeforeClass () throws Exception {

    }

    @Before
    public void setUp () throws Exception {
        // Setting up the units base stats
        Stat stats = new Stat();
        stats.setStatValue("attack", 2);
        stats.setStatValue("defense", 1);

        // Setting up a list of items
        List<Item> itemList = new ArrayList<Item>();

        Map<String, Integer> statMods = new HashMap<String, Integer>();
        statMods.put("attack", 1);

        Map<String, Integer> offensiveStats = new HashMap<String, Integer>();
        offensiveStats.put("attack", 1);

        Map<String, Integer> defensiveStats = new HashMap<String, Integer>();
        defensiveStats.put("defense", 1);

        Map<String, Integer> offensiveOutcomes = new HashMap<String, Integer>();
        defensiveStats.put("attack", 1);

        Map<String, Integer> defensiveOutcomes = new HashMap<String, Integer>();
        defensiveStats.put("defense", -1);

        List<CombatAction> action = new ArrayList<CombatAction>();

        CombatAction combAct =
                new CombatAction(new StatModifier(offensiveStats),
                                 new StatModifier(defensiveStats),
                                 10,
                                 null,
                                 new StatModifier(offensiveOutcomes),
                                 new StatModifier(defensiveOutcomes),
                                 null,
                                 false);
        action.add(combAct);

        Item sword = new Weapon("sword", action, new StatModifier(statMods));

        // Creates Player Character
        playerUnit =
                new GameUnit("Marth", GridConstants.DEFAULT_UNIT_PATH, 0, stats, itemList, true);
        playerUnit.setActiveWeapon(sword);
        // playerUnit.addItem(sword);

        // Creates Enemy
        enemyUnit =
                new GameUnit("Roy", GridConstants.DEFAULT_UNIT_PATH, 0, stats, itemList, true);
        enemyUnit.setActiveWeapon(sword);
        enemyUnit.addItem(sword);
    }

    @Test
    public void testPlayerAttackDamage () {
        System.out.println(playerUnit.getUnitStats().getStatValue("attack"));
        System.out.println(playerUnit.getItemList());

        playerUnit.attack(enemyUnit, playerUnit.getActiveWeapon().getName(), playerUnit
                .getActiveWeapon().getActionList().get(0));

        double enemyHealth = enemyUnit.getHealth();
        double expectedHealth = 11.666;

        assertEquals("Proper Damage Dealt", enemyHealth, expectedHealth, .001);
    }

    // @Test
    // public void testPlayerAttackOutcome () {
    // System.out.println(playerUnit.getStats().getStatValue("attack"));
    // System.out.println(playerUnit.getItemList());
    //
    // playerUnit.attack(enemyUnit, playerUnit.getActiveWeapon().getName(),
    // playerUnit.getActiveWeapon().getActionList().get(0));
    //
    // double newAttack = playerUnit.getStats().getStatValue("attack");
    // double expectedNewAttack = 4;
    //
    // assertEquals("Proper Outcome",newAttack,expectedNewAttack, .001);
    //
    // }

    // @Test
    // public void testEnemyAttack () {
    // fail("Not yet implemented");
    // }

    // @Test
    // public void testPlayerAttackOutcome () {
    // System.out.println(playerUnit.getStats().getStatValue("attack"));
    // System.out.println(playerUnit.getItemList());
    //
    // playerUnit.attack(enemyUnit, playerUnit.getActiveWeapon().getName(),
    // playerUnit.getActiveWeapon().getActionList().get(0));
    //
    // double newAttack = playerUnit.getStats().getStatValue("attack");
    // double expectedNewAttack = 4;
    //
    // assertEquals("Proper Outcome",newAttack,expectedNewAttack, .001);
    //
    // }

    // @Test
    // public void testEnemyAttack () {
    // fail("Not yet implemented");
    // }

}
