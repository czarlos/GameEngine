package gameObject.item;

import gameObject.GameUnit;


/**
 * Items have a name and an quantity (amount), items can have a wide range of effects including
 * effecting stats, which is evident in the statEffect abstract method. Alternatively they can have
 * an effect on the properties of a gameunit, such as reviving a units health.
 * 
 * @author carlosreyes
 * 
 */
public abstract class Item {
    private String name;
    private int amount;

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public int getAmount () {
        return amount;
    }

    public void setAmount (int amount) {
        this.amount = amount;
    }

    /**
     * Sets the stats that this item effects.
     * 
     * @param gameUnit
     */
    public abstract void statEffect (GameUnit gameUnit);

}
