package gameObject.item;

import java.util.ArrayList;
import java.util.List;
import gameObject.GameObject;
import gameObject.action.Action;
import gameObject.action.FixedOutcome;
import gameObject.action.Outcome;


/**
 * Holds a list of items that a unit can retrieve.
 * 
 * @author carlosreyes
 * 
 */
public class Chest extends GameObject {

    private List<Item> myItemList;

    public Chest (List<Item> itemList) {
        myItemList = itemList;
    }

    public List<Item> getItemList () {
        return myItemList;
    }

    public void setItemList (List<Item> itemList) {
        myItemList = itemList;
    }

    // TODO: give objects to person
    @Override
    public Action getInteraction(){
        List<Outcome> initiatorOutcomes = new ArrayList<Outcome>();
        List<Outcome> receiverOutcomes = new ArrayList<Outcome>();
        
        for (Item i : myItemList) {
            initiatorOutcomes.add(new FixedOutcome("Item", i.getName(), i.getAmount()));
            receiverOutcomes.add(new FixedOutcome("Item", i.getName(), -i.getAmount()));
        }
        
        Action getContents = new Action("Retrieve Items", initiatorOutcomes, receiverOutcomes);
        return getContents;
    };
}
