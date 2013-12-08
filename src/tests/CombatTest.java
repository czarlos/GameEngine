package tests;

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
import gameObject.action.ItemOutcome;
import gameObject.action.MasterActions;
import gameObject.action.Outcome;
import gameObject.action.Outcomes;
import gameObject.action.StatOutcome;
import gameObject.item.Item;
import gameObject.item.Weapon;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


public class CombatTest {
    GameUnit playerUnit;
    GameUnit enemyUnit;
    Item potion;

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
    //    playerStats.syncWithMaster();

        Stats enemyStats = new Stats();
    //    enemyStats.syncWithMaster();

        Stats itemStats = new Stats();
    //    itemStats.syncWithMaster();
        itemStats.modExisting("health", 0);
        itemStats.modExisting("defense", 0);

        List<CombatAction> action = new ArrayList<CombatAction>();
        action.add(createStrongAction());
        action.add(createWeakAction());
        action.add(createItemDepletingAction());

        Item sword = new Weapon();

        // Creates Player Character
        playerUnit = new GameUnit();
        playerUnit.setActiveWeapon(sword);

        // Creates Enemy
        enemyUnit = new GameUnit();
        enemyUnit.setActiveWeapon(sword);

        // Creates Item 'potion'
        potion = makeEmptyItem("potion");
    }

    @Test
    public void testPlayerStrongAttackEnemy () {
        CombatAction action = createStrongAction();

        action.doAction(playerUnit, enemyUnit);

        double enemyHealth = enemyUnit.getStat("health");
        double expectedEnemyHealth = 5;

        assertEquals("Proper Enemy Damage Dealt", enemyHealth,
                     expectedEnemyHealth, .001);
    }

    @Test
    public void testPlayerStrongAttackSelf () {
        CombatAction action = createStrongAction();

        action.doAction(playerUnit, enemyUnit);

        double playerHealth = playerUnit.getStat("health");
        double expectedPlayerHealth = 10;

        assertEquals("Proper Self Damage Dealt", playerHealth,
                     expectedPlayerHealth, .001);
    }

    @Test
    public void testPlayerWeakAttack () {
        CombatAction action = createWeakAction();

        action.doAction(playerUnit, enemyUnit);

        double enemyHealth = enemyUnit.getStat("health");
        double expectedEnemyHealth = 11;

        assertEquals("Proper Damage Dealt", enemyHealth, expectedEnemyHealth,
                     .001);
    }

    @Test
    public void testEnemyWeakAttack () {
        CombatAction action = createWeakAction();

        action.doAction(enemyUnit, playerUnit);

        double playerHealth = playerUnit.getStat("health");
        double expectedHealth = 11;

        assertEquals("Proper Damage Dealt", playerHealth, expectedHealth, .001);
    }

    @Test
    public void testPlayerItemDepletingAction () {
        CombatAction action = createItemDepletingAction();

        enemyUnit.addItem(potion);
        enemyUnit.addItem(potion);
        enemyUnit.addItem(potion);
        enemyUnit.addItem(potion);
        enemyUnit.addItem(potion);

        action.doAction(playerUnit, enemyUnit);

        int itemCount = enemyUnit.getItemAmount("potion");
        int expectedItemCount = 3;

        assertEquals("Proper Items Removed", expectedItemCount, itemCount);

    }

    /**
     * Creates an action that deals 10 damage to opponent health at the cost of
     * 5 of the attackers health
     * 
     * @return CombatAction
     */
    public CombatAction createStrongAction () {
        // Creating an action!
        // Requires stats that attack depends on
        // from attacker and defender

        Map<String, Integer> attackerStatsMap = new HashMap<String, Integer>();
        attackerStatsMap.put("attack", 1);
        Stats attackerStats = new Stats();
    //    attackerStats.setStats(attackerStatsMap);

        Map<String, Integer> defenderStatsMap = new HashMap<String, Integer>();
        defenderStatsMap.put("defense", 1);
        Stats defenderStats = new Stats();
    //    defenderStats.setStats(defenderStatsMap);

        Outcomes attackerOutcomes = new Outcomes();
        Outcomes defenderOutcomes = new Outcomes();

        Outcome a1 = new StatOutcome("health", -5, true);
        attackerOutcomes.addOutcome(a1);
        Outcome d1 = new StatOutcome("health", -10, true);
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
     */
    public CombatAction createWeakAction () {
        Map<String, Integer> attackerStatsMap = new HashMap<String, Integer>();
        attackerStatsMap.put("attack", 1);
        Stats attackerStats = new Stats();
    //    attackerStats.setStats(attackerStatsMap);

        Map<String, Integer> defenderStatsMap = new HashMap<String, Integer>();
        defenderStatsMap.put("defense", 1);
        Stats defenderStats = new Stats();
    //    defenderStats.setStats(defenderStatsMap);

        Outcomes attackerOutcomes = new Outcomes();
        Outcomes defenderOutcomes = new Outcomes();

        Outcome d1 = new StatOutcome("health", -4, true);
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
     */
    public CombatAction createItemDepletingAction () {

        Outcomes defenderOutcomes = new Outcomes();

        // removes two potions from opponents item list
        Outcome d1 = new ItemOutcome(potion, -2, true);

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
     */
    public Item makeEmptyItem (String name) {
        Item e = new Item();
        e.setName(name);
        e.setStats(new Stats());

        return e;
    }
}
