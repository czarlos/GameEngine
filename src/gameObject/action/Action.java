package gameObject.action;

import gameObject.GameUnit;

public abstract class Action {
    private String myName;
    
    public abstract void doAction(GameUnit unit1, GameUnit unit2);
    
    public void setName (String name) {
        myName = name;
    }

    public String getName () {
        return myName;
    }
}
