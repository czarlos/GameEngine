/*package tests;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import gameObject.GameUnit;
import gameObject.MasterStats;
import gameObject.Stats;
import gameObject.StatModifier;
import gameObject.action.Action;
import gameObject.action.CombatAction;
import gameObject.action.MasterActions;
import gameObject.action.Outcome;
import gameObject.action.Outcomes;
import gameObject.item.Item;
import gameObject.item.Weapon;
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

        MasterStats masterStat = MasterStats.getInstance();
        masterStat.setStatValue("health", 15);
        masterStat.setStatValue("attack", 2);
        masterStat.setStatValue("defense", 1);

        MasterActions masterActions = MasterActions.getInstance();
        List<Action> myActions = new ArrayList<Action>();
        myActions.add(createStrongAction());
        myActions.add(createWeakAction());
        myActions.add(createItemDepletingAction());
        masterActions.setActionList(myActions);

        Stats playerStats = new Stats();
        playerStats.syncWithMaster();

        Stats enemyStats = new Stats();
        enemyStats.syncWithMaster();

        Stats itemStats = new Stats();
        itemStats.syncWithMaster();
        itemStats.modExisting("health", 0);
        itemStats.modExisting("defense", 0);

        List<CombatAction> action = new ArrayList<CombatAction>();
        action.add(createStrongAction());
        action.add(createWeakAction());
        action.add(createItemDepletingAction());

        Item sword = new Weapon();
        sword.addAction(0);
        sword.addAction(1);
        sword.addAction(2);

        // Creates Player Character
        playerUnit = new GameUnit();
        playerUnit.setActiveWeapon(sword);

        // Creates Enemy
        enemyUnit = new GameUnit();
        enemyUnit.setActiveWeapon(sword);
    }

    @Test
    public void testPlayerStrongAttackEnemy () {
        Weapon weapon = playerUnit.getActiveWeapon();
        CombatAction action = null;

        for (Action ca : weapon.getActions()) {
            if (ca.getName().equals("strong")) {
                action = (CombatAction) ca;
            }
        }

        action.doAction(playerUnit, enemyUnit);

        double enemyHealth = enemyUnit.getStat("health");
        double expectedEnemyHealth = 5;

        assertEquals("Proper Enemy Damage Dealt", enemyHealth,
                     expectedEnemyHealth, .001);
    }

    @Test
    public void testPlayerStrongAttackSelf () {
        Weapon weapon = playerUnit.getActiveWeapon();
        CombatAction action = null;

        for (Action ca : weapon.getActions()) {
            if (ca.getName().equals("strong")) {
                action = (CombatAction) ca;
            }
        }

        action.doAction(playerUnit, enemyUnit);

        double playerHealth = playerUnit.getStat("health");
        double expectedPlayerHealth = 10;

        assertEquals("Proper Self Damage Dealt", playerHealth,
                     expectedPlayerHealth, .001);
    }

    @Test
    public void testPlayerWeakAttack () {
        Weapon weapon = playerUnit.getActiveWeapon();
        CombatAction action = null;

        for (Action ca : weapon.getActions()) {
            if (ca.getName().equals("weak")) {
                action = (CombatAction) ca;
            }
        }

        action.doAction(playerUnit, enemyUnit);

        double enemyHealth = enemyUnit.getStat("health");
        double expectedEnemyHealth = 11;

        assertEquals("Proper Damage Dealt", enemyHealth, expectedEnemyHealth,
                     .001);
    }

    @Test
    public void testEnemyWeakAttack () {
        Weapon weapon = enemyUnit.getActiveWeapon();
        CombatAction action = null;

        for (Action ca : weapon.getActions()) {
            if (ca.getName().equals("weak")) {
                action = (CombatAction) ca;
            }
        }

        action.doAction(enemyUnit, playerUnit);

        double playerHealth = playerUnit.getStat("health");
        double expectedHealth = 11;

        assertEquals("Proper Damage Dealt", playerHealth, expectedHealth, .001);
    }

    @Test
    public void testPlayerItemDepletingAction () {
        Weapon weapon = playerUnit.getActiveWeapon();
        CombatAction action = null;

        for (Action ca : weapon.getActions()) {
            if (ca.getName().equals("item")) {
                action = (CombatAction) ca;
            }
        }

        Item potion = makeEmptyItem("potion");
        enemyUnit.addItem(potion);
        enemyUnit.addItem(potion);
        enemyUnit.addItem(potion);
        enemyUnit.addItem(potion);
        enemyUnit.addItem(potion);

        action.doAction(playerUnit, enemyUnit);

        int itemCount = enemyUnit.getItemAmount("potion");
        int expectedItemCount = 3;

        assertEquals("Proper Items Removed", itemCount, expectedItemCount);

    }
    *//**
     * Creates an action that deals 10 damage to opponent health at the cost of
     * 5 of the attackers health
     * 
     * @return CombatAction
     *//*
    public CombatAction createStrongAction () {
        // Creating an action!
        // Requires stats that attack depends on
        // from attacker and defender

        Map<String, Integer> attackerStatsMap = new HashMap<String, Integer>();
        attackerStatsMap.put("attack", 1);
        Stats attackerStats = new Stats();
        attackerStats.setStats(attackerStatsMap);

        Map<String, Integer> defenderStatsMap = new HashMap<String, Integer>();
        defenderStatsMap.put("defense", 1);
        Stats defenderStats = new Stats();
        defenderStats.setStats(defenderStatsMap);

        Outcomes attackerOutcomes = new Outcomes();
        Outcomes defenderOutcomes = new Outcomes();

        Outcome a1 = new Outcome("health", -5, true);
        attackerOutcomes.addOutcome(a1);
        Outcome d1 = new Outcome("health", -10, true);
        defenderOutcomes.addOutcome(d1);

        CombatAction ca = new CombatAction();
        ca.setName("strong");
        ca.setInitiatorStatWeights(attackerStats);
        ca.setReceiverStatWeights(defenderStats);
        ca.setInitiatorOutcomes(attackerOutcomes);
        ca.setReceiverOutcomes(defenderOutcomes);
        return ca;
    }

    /**
     * Creates an action that deals 4 damage to the opponent health
     * 
     * @return CombatAction
     
    public CombatAction createWeakAction () {
        Map<String, Integer> attackerStatsMap = new HashMap<String, Integer>();
        attackerStatsMap.put("attack", 1);
        Stats attackerStats = new Stats();
        attackerStats.setStats(attackerStatsMap);

        Map<String, Integer> defenderStatsMap = new HashMap<String, Integer>();
        defenderStatsMap.put("defense", 1);
        Stats defenderStats = new Stats();
        defenderStats.setStats(defenderStatsMap);

        Outcomes attackerOutcomes = new Outcomes();
        Outcomes defenderOutcomes = new Outcomes();

        Outcome d1 = new Outcome("health", -4, true);
        defenderOutcomes.addOutcome(d1);

        CombatAction ca = new CombatAction();
        ca.setName("weak");
        ca.setInitiatorStatWeights(attackerStats);
        ca.setReceiverStatWeights(defenderStats);
        ca.setInitiatorOutcomes(attackerOutcomes);
        ca.setReceiverOutcomes(defenderOutcomes);
        return ca;
    }

    /**
     * Creates and action that removes two potions from enemy inventory
     * 
     * @return CombatAction
     
    public CombatAction createItemDepletingAction () {

        Outcomes defenderOutcomes = new Outcomes();

        // removes two potions from opponents item list
        Outcome d1 = new Outcome("potion", -2, true);

        defenderOutcomes.addOutcome(d1);

        CombatAction ca = new CombatAction();
        ca.setName("item");
        ca.setReceiverOutcomes(defenderOutcomes);
        return ca;
    }


    /**
     * Function to create empty items to test item deletion
     * 
     * @param name
     *        - name of item
     * 
     * @return Item - created items
     
    public Item makeEmptyItem (String name) {
        Item e = new Item();
        e.setName(name);
        e.setStats(new Stats());

        return e;
    }
}
*/