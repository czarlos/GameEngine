package gameObject;

import gameObject.item.Item;
import grid.GridConstants;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;


public class Shop extends Chest {

    @Override
    @JsonIgnore
    public List<String> getInteractions () {
        if (myItems.isEmpty()) { return null; }
        List<String> interactions = new ArrayList<>();
        for (Item item : myItems) {
            interactions.add(GridConstants.SHOP + " " + item.getName());
        }
        return interactions;
    }
}
