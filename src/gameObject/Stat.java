package gameObject;

import view.Customizable;


public class Stat extends Customizable {
    private int myValue;

    public Stat (int value) {
        myValue = value;
    }

    public int getValue () {
        return myValue;
    }

    public void setValue (int newValue) {
        myValue = newValue;
    }

    @Override
    public Stat clone () {
        Stat newStat = new Stat(myValue);
        newStat.setLastIndex(myLastIndex);
        newStat.setImagePath(myImagePath);
        newStat.setName(myName);

        return newStat;
    }
}
