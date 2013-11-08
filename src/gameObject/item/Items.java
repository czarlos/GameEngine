package gameObject.item;

import grid.GameUnit;


public abstract class Items {
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
