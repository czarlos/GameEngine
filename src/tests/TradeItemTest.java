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
    Item item1;
    Item item2;
    Item item3;
    Item item4;
    TradeAction ta;
    
    @Before
    public void setUp () throws Exception {
        // Generate Item
        item1 = makeEmptyItem("item");
        item2 = makeEmptyItem("shoe");
        item3 = makeEmptyItem("pants");
        item4 = makeEmptyItem("shirt");
        
        // Set Up Units to test
        playerUnit = new GameUnit();
        playerUnit.setAffiliation("player");
        allyUnit = new GameUnit();
        allyUnit.setAffiliation("player");
        allyUnit.addItem(item1);
        allyUnit.addItem(item1);
        
        // Set Up Trade Action
        ta = new TradeAction(allyUnit.getInteractions().get(0).split(" ")[1]);
    }

    @Test
    public void testPlayerBeforeTrade () {
        assertTrue("Unit does not have 'item' in its set",!playerUnit.getItems().contains(item1));
        assertEquals(playerUnit.getItemAmount(item1.getName()), 0);
        assertEquals(playerUnit.getItem("item"), null);
    }
    
    @Test
    public void testPlayerAfterTrade () {
        ta.doAction(playerUnit, allyUnit);
        
        assertTrue("Unit does have 'item' in its set",playerUnit.getItems().contains(item1));
        assertEquals(playerUnit.getItemAmount(item1.getName()), 2);
        assertEquals(playerUnit.getItem("item"), item1);
    }
    
    @Test
    public void testAllyBeforeTrade () {
        assertTrue("Unit does have 'item' in its set",allyUnit.getItems().contains(item1));
        assertEquals(allyUnit.getItemAmount(item1.getName()), 2);
        assertEquals(allyUnit.getItem("item"), item1);
    }
    
    @Test
    public void testAllyAfterTrade () {
        ta.doAction(playerUnit, allyUnit);
        
        assertTrue("Unit does not have 'item' in its set",!allyUnit.getItems().contains(item1));
        assertEquals(allyUnit.getItemAmount(item1.getName()), 0);
        assertEquals(allyUnit.getItem("item"), null);
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
