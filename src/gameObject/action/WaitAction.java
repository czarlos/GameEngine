package gameObject.action;

import gameObject.GameUnit;

public class WaitAction extends Action {
    private final static String NAME = "WaitAction";
    
    public WaitAction () {
        super.setName(NAME);
    }
    
    @Override
    void doAction (GameUnit unit1, GameUnit unit2) {
        
    }
    
}
