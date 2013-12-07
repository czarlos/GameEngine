package tests;

import static org.junit.Assert.*;
import gameObject.GameUnit;
import gameObject.Stats;
import gameObject.action.TradeAction;
import gameObject.item.Item;
import org.junit.Before;
import org.junit.Test;

public class TradeItemTest {
    GameUnit playerUnit;
    GameUnit allyUnit;
    Item item;
    TradeAction ta;
    
    @Before
    public void setUp () throws Exception {
        // Generate Item
        item = makeEmptyItem("item");
        
        // Set Up Units to test
        playerUnit = new GameUnit();
        playerUnit.setAffiliation("player");
        allyUnit = new GameUnit();
        allyUnit.setAffiliation("player");
        allyUnit.addItem(item);
        
        // Set Up Trade Action
        ta = new TradeAction(allyUnit.getInteractions().get(0).split(" ")[1]);
    }

    @Test
    public void testPlayerBeforeTrade () {
        fail("Not yet implemented");
    }
    
    @Test
    public void testPlayerAfterTrade () {
        System.out.println("Player item value: "+playerUnit.combatGetItemValue(item));
        System.out.println("Ally item value: "+allyUnit.combatGetItemValue(item));
        
        ta.doAction(playerUnit, allyUnit);
        
        System.out.println("Player item value: "+playerUnit.combatGetItemValue(item));
        System.out.println("Ally item value: "+allyUnit.combatGetItemValue(item));
        
        System.out.println("Player item set: "+playerUnit.getItems());
        
        assertTrue(playerUnit.getItems().contains(item));
        assertEquals(playerUnit.getItem("item"), item);
    }
    
    @Test
    public void testAllyBeforeTrade () {
        fail("Not yet implemented");
    }
    
    @Test
    public void testAllyAfterTrade () {
        fail("Not yet implemented");
    }

    /**
     * Function to create empty items to test item interactions
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
