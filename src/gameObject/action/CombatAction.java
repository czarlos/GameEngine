package gameObject.action;

import gameObject.GameUnit;
import gameObject.Stat;
import gameObject.StatModifier;
import java.util.List;


public class CombatAction extends Action {
    
    private Stat myStats;
    
    public CombatAction () {
    }
    
    
    public void setStats(Stat stats) {
        myStats = stats;
    }
    
    @Override
    void doAction (GameUnit unit1, GameUnit unit2) {
        
    }

}
