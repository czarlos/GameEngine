package gameObject;

import game.ImageManager;


public class Stat extends Customizable {
    private int myValue;

    public Stat () {
    }

    public Stat (String name) {
        myValue = 0;
        myName = name;
        myImagePath = "resources/grass.png";
        myImage = ImageManager.getImage(myImagePath);
    }

    public int getValue () {
        return myValue;
    }

    public void setValue (int newValue) {
        myValue = newValue;
    }

    @Override
    public Stat clone () {
        Stat newStat = new Stat();
        newStat.setValue(myValue);
        newStat.setLastIndex(myLastIndex);
        newStat.setImagePath(myImagePath);
        newStat.setName(myName);

        return newStat;
    }
}
