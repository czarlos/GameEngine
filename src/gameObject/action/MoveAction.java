package gameObject.action;

import gameObject.GameUnit;

public class MoveAction extends Action {
    private static final String NAME = "MoveAction";
    
    public MoveAction () {
        super.setName(NAME);
    }
    
    @Override
    void doAction (GameUnit unit1, GameUnit unit2) {
        
    }
    
}
