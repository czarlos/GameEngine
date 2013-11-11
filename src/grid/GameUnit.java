package grid;

import gameObject.Items;
import gameObject.Stats;
import java.awt.Image;
import java.util.List;


public class GameUnit extends GameObject {
    public GameUnit () {
        super();
    }

    private boolean myControllable;
    private List<Items> myItemsList;
    private Stats myUnitStats;
    private String myAffiliation;

    public Stats getStats () {
        return myUnitStats;
    }

}
