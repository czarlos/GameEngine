package tests;

import static org.junit.Assert.assertEquals;
import org.junit.Assert.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import gameObject.GameUnit;
import gameObject.Stat;
import gameObject.StatModifier;
import gameObject.action.CombatAction;
import gameObject.action.FixedOutcome;
import gameObject.action.Outcome;
import gameObject.item.Equipment;
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
        Stat playerStats = new Stat();
        playerStats.setStatValue("health", 15);
        playerStats.setStatValue("attack", 2);
        playerStats.setStatValue("defense", 1);

        Stat enemyStats = new Stat();
        enemyStats.setStatValue("health", 15);
        enemyStats.setStatValue("attack", 2);
        enemyStats.setStatValue("defense", 1);

        // Setting up a list of items
        List<Item> itemList = new ArrayList<>();

        Map<String, Integer> itemStatsMap = new HashMap<String, Integer>();
        itemStatsMap.put("attack", 1);
        StatModifier itemStats = new StatModifier();

        List<CombatAction> action = new ArrayList<CombatAction>();
        action.add(createStrongAction());
        action.add(createWeakAction());
        action.add(createItemDepletingAction());

        Item sword = new Weapon("sword", action, itemStats);

        // Creates Player Character
        playerUnit = new GameUnit();
        playerUnit.setActiveWeapon(sword);
        // playerUnit.addItem(sword);

        // Creates Enemy
        enemyUnit = new GameUnit();
        enemyUnit.setActiveWeapon(sword);
    }

    @Test
    public void testPlayerStrongAttackEnemy () {
        Weapon weapon = enemyUnit.getActiveWeapon();
        CombatAction action = null;
        for (CombatAction ca : weapon.getActionList()) {
            if (ca.getName().equals("Strong")) {
                action = ca;
            }
        }

        playerUnit.attack(enemyUnit, weapon.getName(), action);

        double enemyHealth = enemyUnit.getStat("health");
        double expectedEnemyHealth = 5;

        assertEquals("Proper Enemy Damage Dealt", enemyHealth, expectedEnemyHealth, .001);
    }

    @Test
    public void testPlayerStrongAttackSelf () {
        Weapon weapon = enemyUnit.getActiveWeapon();
        CombatAction action = null;
        for (CombatAction ca : weapon.getActionList()) {
            if (ca.getName().equals("Strong")) {
                action = ca;
            }
        }

        playerUnit.attack(enemyUnit, weapon.getName(), action);

        double playerHealth = playerUnit.getStat("health");
        double expectedPlayerHealth = 10;

        assertEquals("Proper Self Damage Dealt", playerHealth, expectedPlayerHealth, .001);
    }

    @Test
    public void testPlayerWeakAttack () {
        Weapon weapon = enemyUnit.getActiveWeapon();
        CombatAction action = null;
        for (CombatAction ca : weapon.getActionList()) {
            if (ca.getName().equals("Weak")) {
                action = ca;
            }
        }

        playerUnit.attack(enemyUnit, weapon.getName(), action);

        double enemyHealth = enemyUnit.getStat("health");
        double expectedEnemyHealth = 11;

        assertEquals("Proper Damage Dealt", enemyHealth, expectedEnemyHealth, .001);
    }

    @Test
    public void testEnemyWeakAttack () {
        Weapon weapon = enemyUnit.getActiveWeapon();
        CombatAction action = null;
        for (CombatAction ca : weapon.getActionList()) {
            if (ca.getName().equals("Weak")) {
                action = ca;
            }
        }

        enemyUnit.attack(playerUnit, weapon.getName(), action);

        double playerHealth = playerUnit.getStat("health");
        double expectedHealth = 11;

        assertEquals("Proper Damage Dealt", playerHealth, expectedHealth, .001);
    }

    @Test
    public void testPlayerItemDepletingAction () {
        Weapon weapon = enemyUnit.getActiveWeapon();
        CombatAction action = null;
        for (CombatAction ca : weapon.getActionList()) {
            if (ca.getName().equals("ItemDepleting")) {
                action = ca;
            }
        }

        enemyUnit.addItem(makeEmptyItem("potion", 5));

        playerUnit.attack(enemyUnit, weapon.getName(), action);

        int itemCount = enemyUnit.getItemCount("potion");
        int expectedItemCount = 3;

        assertEquals("Proper Items Removed", itemCount, expectedItemCount);
    }

    /**
     * Creates an action that deals 10 damage to opponent health at the cost of 5 of the attackers
     * health
     * 
     * @return CombatAction
     */
    public CombatAction createStrongAction () {
        // Creating an action!
        // Requires stats that attack depends on
        // from attacker and defender

        Map<String, Integer> attackerStatsMap = new HashMap<String, Integer>();
        attackerStatsMap.put("attack", 1);
        StatModifier attackerStats = new StatModifier(attackerStatsMap);

        Map<String, Integer> defenderStatsMap = new HashMap<String, Integer>();
        defenderStatsMap.put("defense", 1);
        StatModifier defenderStats = new StatModifier(defenderStatsMap);

        List<Outcome> attackerOutcomes = new ArrayList<>();
        List<Outcome> defenderOutcomes = new ArrayList<>();

        Outcome a1 = new FixedOutcome("Stat", "health", -5);
        attackerOutcomes.add(a1);
        Outcome d1 = new FixedOutcome("Stat", "health", -10);
        defenderOutcomes.add(d1);

        return new CombatAction("Strong", attackerStats, defenderStats,
                                attackerOutcomes, defenderOutcomes);
    }

    /**
     * Creates an action that deals 4 damage to the opponent health
     * 
     * @return CombatAction
     */
    public CombatAction createWeakAction () {
        Map<String, Integer> attackerStatsMap = new HashMap<String, Integer>();
        attackerStatsMap.put("attack", 1);
        StatModifier attackerStats = new StatModifier(attackerStatsMap);

        Map<String, Integer> defenderStatsMap = new HashMap<String, Integer>();
        defenderStatsMap.put("defense", 1);
        StatModifier defenderStats = new StatModifier(defenderStatsMap);

        List<Outcome> attackerOutcomes = new ArrayList<>();
        List<Outcome> defenderOutcomes = new ArrayList<>();

        Outcome d1 = new FixedOutcome("Stat", "health", -4);

        defenderOutcomes.add(d1);

        return new CombatAction("Weak", attackerStats, defenderStats,
                                attackerOutcomes, defenderOutcomes);
    }

    /**
     * Creates and action that removes two potions from enemy inventory
     * 
     * @return CombatAction
     */
    public CombatAction createItemDepletingAction () {

        Map<String, Integer> attackerStatsMap = new HashMap<String, Integer>();
        attackerStatsMap.put("attack", 1);
        StatModifier attackerStats = new StatModifier(attackerStatsMap);

        Map<String, Integer> defenderStatsMap = new HashMap<String, Integer>();
        defenderStatsMap.put("defense", 1);
        StatModifier defenderStats = new StatModifier(defenderStatsMap);

        List<Outcome> attackerOutcomes = new ArrayList<>();
        List<Outcome> defenderOutcomes = new ArrayList<>();

        // removes two potions from opponents item list
        Outcome d1 = new FixedOutcome("Item", "potion", -2);

        defenderOutcomes.add(d1);

        return new CombatAction("ItemDepleting", attackerStats, defenderStats,
                                attackerOutcomes, defenderOutcomes);
    }

    /**
     * Function to create empty items to test item deletion
     * 
     * @param name - name of item
     * @param quantity - number of them
     * @return Item - created items
     */
    public Item makeEmptyItem (String name, int quantity) {
        Equipment e = new Equipment(name, new StatModifier());
        e.setAmount(quantity);
        e.setModifier(new StatModifier(new HashMap<String, Integer>()));

        return e;
    }

}
