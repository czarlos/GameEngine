package team;

import gameObject.GameUnit;
import gameObject.UnitFactory;
import java.util.ArrayList;
import java.util.List;

public class Team {
    private List<GameUnit> myGameUnits;
    private int myGold;
    private int myAffiliation;
    
    public List<UnitFactory> getFactories () {
        List<UnitFactory> factoryList = new ArrayList<UnitFactory>();
        for(GameUnit unit : myGameUnits) {
            if (unit instanceof UnitFactory) {
                factoryList.add((UnitFactory) unit);
            }
        }
        return factoryList;
    }

    public int getGold () {
        return myGold;
    }

    public void setGold (int myGold) {
        this.myGold = myGold;
    }

    public int getAffiliation () {
        return myAffiliation;
    }

    public void setAffiliation (int myAffiliation) {
        this.myAffiliation = myAffiliation;
    }
}
