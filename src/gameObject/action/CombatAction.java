package gameObject.action;

import gameObject.GameObject;
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
    public void doAction (GameUnit initiator, GameObject receiver) {
        
    }

	@Override
	public boolean isValidAction(GameUnit gameUnit, GameObject gameObject) {
		// TODO Auto-generated method stub
		return false;
	}

}
