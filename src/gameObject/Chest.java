package gameObject;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import gameObject.action.ChestAction;


/**
 * Holds a set of items that a unit can retrieve.
 * 
 * @author Kevin, Andy
 * 
 */
@JsonAutoDetect
public class Chest extends InventoryObject {

    public Chest () {
    }

    public boolean isEmpty () {
        return myItems.isEmpty();
    }

    @Override
    @JsonIgnore
    public List<String> getInteractions () {
        if (myItems.isEmpty()) { return null; }
        List<String> actions = new ArrayList<String>();
        actions.add(new ChestAction().getName());
        return actions;
    }
}
