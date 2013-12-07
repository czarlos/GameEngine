package gameObject.item;

import gameObject.action.ChestAction;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class Shop extends Chest {

    
    
    @Override
    @JsonIgnore
    public List<String> getInteractions () {
        List<String> actions = new ArrayList<String>();
        actions.add(new ChestAction().getName());
        return actions;
    }
}
