package gameObject;

import java.util.List;


public class GameUnit extends GameObject {

    private boolean myControllable;
    private List<Items> myItemsList;
    private Stats myUnitStats;
    private String myAffiliation;

    public GameUnit (String name,
                     String imagePath,
                     String affiliation,
                     Stats stats,
                     List<Items> items,
                     boolean controllable) {
        super(name, imagePath);
        myAffiliation = affiliation;
        myUnitStats = stats;
        myItemsList = items;
        myControllable = controllable;
    }

    public Stats getStats () {
        return myUnitStats;
    }

}
