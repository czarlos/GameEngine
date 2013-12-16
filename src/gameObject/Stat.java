package gameObject;

import game.ImageManager;


/**
 * A stat in the game. Contains a name and a value
 * 
 * @author Ken McAndrews
 * 
 */
public class Stat extends Customizable {
    private int myValue;

    public Stat () {
    }

    /**
     * Constructor for a Stat. Sets value to 0, name to whatever is passed in, and image to default
     * values
     * 
     * @param name Name to set the stat's name to
     */
    public Stat (String name) {
        myValue = 0;
        myName = name;
        myImagePath = "resources/grass.png";
        myImage = ImageManager.getImage(myImagePath);
    }

    /**
     * @return The stat's value
     */
    public int getValue () {
        return myValue;
    }

    /**
     * Sets the stat's value
     * 
     * @param newValue The new value to set the stat to
     */
    public void setValue (int newValue) {
        myValue = newValue;
    }

    /**
     * Clones the stat
     * 
     * @return The instance of the cloned stat
     */
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
