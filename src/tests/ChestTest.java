package tests;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;
import gameObject.GameUnit;
import gameObject.Stats;
import gameObject.item.Chest;
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
        List<Item> itemList = new ArrayList<>();
        
        potion = makeEmptyItem("potion");
        key = makeEmptyItem("key");
        
        itemList.add(potion);
        itemList.add(key);
        
        chest = new Chest(itemList);
        
        playerUnit = new GameUnit();
    }

    @Test
    public void testPlayerBeforeChestAction () {
        assertTrue(!playerUnit.getItems().contains(key));
        assertTrue(!playerUnit.getItems().contains(potion));
    }
    
    @Test
    public void testPlayerAfterChestAction () {
        chest.getInteraction().doAction(playerUnit, chest);
        
        assertTrue(playerUnit.getItems().contains(potion));
        assertTrue(playerUnit.getItems().contains(key));
    }
    
    @Test
    public void testChestBeforeChestAction () {
        assertTrue(chest.getItemList().contains(potion));
        assertTrue(chest.getItemList().contains(key));
    }
    
    @Test
    public void testChestAfterChestAction () {
        chest.getInteraction().doAction(playerUnit, chest);
        
        assertTrue(!chest.getItemList().contains(potion));
        assertTrue(!chest.getItemList().contains(key));
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
