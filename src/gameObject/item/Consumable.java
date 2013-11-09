package gameObject.item;

import grid.GameUnit;


/**
 * Consumables are a very specific type of item which can be used to modify
 * a characters 'properties' such as their health.
 * 
 * @author carlosreyes
 * 
 */
public class Consumable extends Items {
    private int amount;

    @Override
    public void statEffect (GameUnit gameUnit) {
    }
}
