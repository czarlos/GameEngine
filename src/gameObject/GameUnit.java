package gameObject;

import gameObject.item.Items;
import java.util.List;


public class GameUnit extends GameObject {

    private boolean isControllable;
    private boolean isActive;
    private List<Items> myItemsList;
    private Stat myUnitStats;
    private String myAffiliation;

    public GameUnit (String name,
                     String imagePath,
                     String affiliation,
                     Stat stats,
                     List<Items> items,
                     boolean controllable) {
        super(name, imagePath);
        myAffiliation = affiliation;
        myUnitStats = stats;
        myItemsList = items;
        isControllable = controllable;
    }

    public Stat getStats () {
        return myUnitStats;
    }
    
    public void startTurn() {
        isActive = true;
    }
    
    public void endTurn() {
        isActive = false;
    }

}
