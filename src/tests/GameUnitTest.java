
package tests;

import static org.junit.Assert.*;
import gameObject.GameUnit;
import org.junit.Test;


/**
 * Test for GameUnit class
 * 
 * @author carlosreyes
 * 
 */
public class GameUnitTest {
    private GameUnit customUnit;
    private GameUnit defaultUnit;

    public void initializeDefault () {
        defaultUnit = new GameUnit();
    }

    /**
     * Initialized the unit 'customUnit' a non-default unit used for testing
     * unit, combat, item, and interaction functionality.
     */

    // public void initializeCustom () {
    // // Setting up the units base stats
    // Stats stats = new Stats();
    // stats.setStatValue("attack", 10);
    // stats.setStatValue("defense", 5);
    // stats.setStatValue("agility", 7);
    // stats.setStatValue("health", 15);
    //
    // Map<String, Integer> statMods = new HashMap<String, Integer>();
    // statMods.put("attack", 4);
    // List<CombatAction> action = new ArrayList<CombatAction>();
    // CombatAction combAct =
    // new CombatAction("Test", new StatModifier(statMods), null, null, null);
    // action.add(combAct);
    // Item sword = new Weapon("sword", action, new StatModifier(statMods));
    //
    // Map<String, Integer> statMods1 = new HashMap<String, Integer>();
    // statMods1.put("defense", 3);
    // Item shield = new Equipment("shield", new StatModifier(statMods1));
    //
    // customUnit = new GameUnit();
    // customUnit.setActiveWeapon(sword);
    // // Note this is how all items must be added.
    // customUnit.addItem(sword);
    // customUnit.addItem(shield);
    // }

    @Test
    public void testName () {
        assert (customUnit.getName().equals("Marth"));
    }

    @Test
    public void testStats () {
        // initializeCustom();
        assertEquals(customUnit.getStats().getStatValue("agility"), 7, 0);
        assertEquals(customUnit.getStats().getStatValue("health"), 15, 0);

    }

    @Test
    public void testItems () {
        // initializeCustom();
        assertEquals(customUnit.getStats().getStatValue("attack"), 14, 0.1);
    }

    @Test
    public void testDefaultStat () {
        initializeDefault();
        assertEquals(defaultUnit.getStats().getStatValue("movement"), 3, 0);
    }
}
