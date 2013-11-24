package gameObject.action;

import gameObject.GameUnit;
import gameObject.Stats;


public class CombatAction extends Action {
    
    private Stats myStats;
    
    public CombatAction () {
    }    
    
    public void setStats(Stats stats) {
        myStats = stats;
    }
    
    @Override
    public void doAction (GameUnit unit1, GameUnit unit2) {
        
    }

}
