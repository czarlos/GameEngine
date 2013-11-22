package gameObject;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class FixedOutcome extends Outcome {

    public FixedOutcome (String type, String name, int amount) {
        super(type, name, amount);
    }

    /**
     * Changes the amount of a supplies type, name, and amount in a fixed way,
     * such that no stat interaction affects it. If it would make a value negative, makes it zero.
     * 
     * @param unit
     * @param effectiveness
     */
    @Override
    public void applyOutcome (GameUnit unit, double effectiveness) {
        try {
            Method get = unit.getClass().getDeclaredMethod("get" + myType,
                                                           String.class);
            Method set = unit.getClass().getDeclaredMethod("set" + myType,
                                                           String.class, int.class);

            int newAmount = (int) get.invoke(unit, myName) + myAmount;

            newAmount = (newAmount > 0 ? newAmount : 0);

            set.invoke(unit, myName, newAmount);

        }
        catch (NoSuchMethodException | SecurityException
                | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException e) {
            // TODO Figure out what to do
            e.printStackTrace();
        }
    }

    /**
     * Checks whether a unit should be able to perform an action with this outcome against another
     * unit
     * 
     * @return whether or not outcome is legal
     */
    @Override
    public boolean checkValidOutcome (GameUnit unit, double effectiveness) {
        try {
            Method get = unit.getClass().getDeclaredMethod("get" + myType,
                                                           String.class);

            int oldAmount = (int) get.invoke(unit, myName);
            int newAmount = (int) get.invoke(unit, myName) + myAmount;

            if (oldAmount - newAmount < 0) { return false; }

        }
        catch (NoSuchMethodException | SecurityException
                | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException e) {
            // TODO Figure out what to do
            e.printStackTrace();
        }

        return true;
    }

}
