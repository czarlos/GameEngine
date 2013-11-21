package gameObject;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class FixedOutcome extends Outcome {

    public FixedOutcome (String type, String name, int amount) {
        super(type, name, amount);
    }

    /**
     * Changes the amount of a supplies type, name, and amount in a fixed way,
     * such that no stat interaction affects it.
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

            set.invoke(unit, myName, newAmount);

        }
        catch (NoSuchMethodException | SecurityException
                | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException e) {
            // TODO Figure out what to do
            e.printStackTrace();
        }
    }

}
