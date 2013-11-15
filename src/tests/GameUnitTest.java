package tests;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import gameObject.GameUnit;
import gameObject.Stat;
import gameObject.StatModifier;
import gameObject.item.Armor;
import gameObject.item.Item;
import gameObject.Properties;
import gameObject.item.Weapon;
import grid.GridConstants;
import org.junit.Test;
import action.CombatAction;


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
     * Initialized the unit 'customUnit' a non-default
     * unit used for testing unit, combat, item, and interaction
     * functionality.
     */
    public void initializeCustom () {
        // Setting up the units base stats
        Stat stats = new Stat();
        stats.setStatValue("attack", 10);
        stats.setStatValue("defense", 5);
        stats.setStatValue("agility", 7);
        stats.setStatValue("health", 15);

        // Setting up a list of items
        List<Item> itemList = new ArrayList<Item>();

        Map<String, Integer> statMods = new HashMap<String, Integer>();
        statMods.put("attack", 4);
        List<CombatAction> action = new ArrayList<CombatAction>();
        CombatAction combAct =
                new CombatAction(new StatModifier(statMods), null, statMods, null, null, null,
                                 false);
        action.add(combAct);
        Item sword = new Weapon("sword", action, new StatModifier(statMods));

        Map<String, Integer> statMods1 = new HashMap<String, Integer>();
        statMods1.put("defense", 3);
        Item shield = new Armor("shield", new StatModifier(statMods1));

        Properties properties = new Properties(15, 0);
        customUnit =
                new GameUnit("Marth", GridConstants.DEFAULT_UNIT_PATH, 0, stats, itemList, true,
                             properties);
        customUnit.setActiveWeapon(sword);
        // Note this is how all items must be added.
        customUnit.addItem(sword);
        customUnit.addItem(shield);
    }

    @Test
    public void testName () {
        assert (customUnit.getName().equals("Marth"));
    }

    @Test
    public void testStats () {
        initializeCustom();
        assertEquals(customUnit.getStats().getStatValue("agility"), 7, 0);
        assertEquals(customUnit.getStats().getStatValue("health"), 15, 0);

    }

    @Test
    public void testArmor () {
        initializeCustom();
        Map<String, Integer> statMods = new HashMap<String, Integer>();
        statMods.put("defense", 20);
        Item helmet = new Armor("helmet", new StatModifier(statMods));
        customUnit.addItem(helmet);
        assertEquals(customUnit.getStats().getStatValue("defense"), 28, 0);
    }

    @Test
    public void testProperties () {
        initializeCustom();
        System.out.println(customUnit.getProperties());
        double health = customUnit.getProperties().getHealth();
        double exp = customUnit.getProperties().getExperience();
        assertEquals(health, 15, 0);
        assertEquals(exp, 0, 0);

    }

    @Test
    public void testItems () {
        initializeCustom();
        assertEquals(customUnit.getStats().getStatValue("attack"), 14, 0.1);
    }

    @Test
    public void testCurrentWeapon () {
        initializeCustom();
        assert (customUnit.getActiveWeapon().equals("sword"));
    }

    @Test
    public void testRemoveItem () {
        initializeCustom();
        Map<String, Integer> statMods = new HashMap<String, Integer>();
        statMods.put("defense", 20);
        Item helmet = new Armor("helmet", new StatModifier(statMods));
        customUnit.addItem(helmet);
        assertEquals(customUnit.getStats().getStatValue("defense"), 28, 0);

        customUnit.removeItem(helmet);
        assertEquals(customUnit.getStats().getStatValue("defense"), 8, 0);
    }

    @Test
    public void addItem () {
        initializeCustom();
        Map<String, Integer> statMods = new HashMap<String, Integer>();
        statMods.put("agility", 15);
        statMods.put("attack", 10);
        Item staff = new Armor("staff", new StatModifier(statMods));
        customUnit.addItem(staff);
        assertEquals(customUnit.getStats().getStatValue("agility"), 22, 0);
        assertEquals(customUnit.getStats().getStatValue("attack"), 24, 0);

    }

    @Test
    public void testDefaultStat () {
        initializeDefault();
        assertEquals(defaultUnit.getStats().getStatValue("movement"), 3, 0);
    }

    @Test
    public void testAddItemToDefault () {
        initializeDefault();

        Map<String, Integer> statMods = new HashMap<String, Integer>();
        statMods.put("agility", 24);
        statMods.put("attack", 28);
        statMods.put("defense", 5);

        List<CombatAction> action = new ArrayList<CombatAction>();
        CombatAction combAct =
                new CombatAction(new StatModifier(statMods), null, statMods, null, null, null,
                                 false);
        action.add(combAct);

        Item bow = new Weapon("bow", action, new StatModifier(statMods));

        Stat stats = new Stat();
        stats.setStatValue("agility", 0);
        stats.setStatValue("attack", 0);
        stats.setStatValue("defense", 0);

        defaultUnit.setUnitStats(stats);

        defaultUnit.addItem(bow);
        defaultUnit.setActiveWeapon(bow);
        assertEquals(defaultUnit.getStats().getStatValue("attack"), 28, 0);
        assertEquals(defaultUnit.getActiveWeapon(), bow);
    }

    @Test
    public void testDefaultName () {
        initializeDefault();
        assertEquals(defaultUnit.getName(), GridConstants.DEFAULT_UNIT_NAME);
    }
}
