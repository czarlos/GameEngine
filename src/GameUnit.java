import java.util.List;


public class GameUnit extends GameObject {
    private boolean myControllable;
    private List<Items> myItemsList;
    private Stats myUnitStats;
    private String myAffiliation;

    public Stats getStats () {
        return myUnitStats;
    }
}
