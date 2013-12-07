package tests;

import static org.junit.Assert.*;
import java.util.HashSet;
import java.util.Set;
import gameObject.Chest;
import gameObject.GameUnit;
import gameObject.Stats;
import gameObject.action.ChestAction;
import gameObject.item.Item;
import org.junit.Before;
import org.junit.Test;


public class ChestTest {
    GameUnit playerUnit;
    Chest chest;
    Item potion;
    Item key;

    @Before
    public void setUp () throws Exception {
        Set<Item> itemList = new HashSet<>();

        potion = makeEmptyItem("potion");
        key = makeEmptyItem("key");

        itemList.add(potion);
        itemList.add(key);

        chest = new Chest();
        chest.setItems(itemList);

        playerUnit = new GameUnit();
    }

    @Test
    public void testPlayerBeforeChestAction () {
        assertTrue(!playerUnit.getItems().contains(key));
        assertTrue(!playerUnit.getItems().contains(potion));
    }

    @Test
    public void testPlayerAfterChestAction () {
        ChestAction ca = new ChestAction();
        ca.doAction(playerUnit, chest);

        assertTrue(playerUnit.getItems().contains(potion));
        assertTrue(playerUnit.getItems().contains(key));
    }

    @Test
    public void testChestBeforeChestAction () {
        assertTrue(chest.getItems().contains(potion));
        assertTrue(chest.getItems().contains(key));
    }

    @Test
    public void testChestAfterChestAction () {
        ChestAction ca = new ChestAction();
        ca.doAction(playerUnit, chest);

        assertTrue(!chest.getItems().contains(potion));
        assertTrue(!chest.getItems().contains(key));
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
