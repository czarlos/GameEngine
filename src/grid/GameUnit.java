package grid;

import gameObject.Items;
import gameObject.Stats;
import java.util.List;


public class GameUnit extends GameObject {
    public GameUnit (String name, String imagePath) {
        super(name, imagePath);
        // TODO Auto-generated constructor stub
    }

    private boolean myControllable;
    private List<Items> myItemsList;
    private Stats myUnitStats;
    private String myAffiliation;

    public Stats getStats () {
        return myUnitStats;
    }

}
