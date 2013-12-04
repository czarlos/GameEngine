package gameObject.action;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import gameObject.GameUnit;

@JsonAutoDetect
public class Outcome {
    private String myType;
    private String myName;
    private int myAmount;
    private boolean isFixed;

    public Outcome (){
        
    }
    
    public Outcome (String name, int amount, boolean fixed) {
        myName = name;
        myAmount = amount;
        isFixed = fixed;
    }    

    /**
     * applyOutcomes edits a GameUnit based on user specified type, name, and
     * amount. These outcomes are affected by stat differences between units
     * (effectiveness).
     * 
     * @param unit
     *        - GameUnit where values are being edited
     * @param effectiveness
     *        - A measurement of how much of an outcome should occur
     */
    public void applyOutcome (GameUnit unit, double effectiveness) {
        
        if (isStat(unit, myName)) {
            myType = "Stat";
        }
        else {
            myType = "Item";
        }
        
        try {
            Method get = unit.getClass().getDeclaredMethod("get" + myType,
                                                           String.class);
            Method set = unit.getClass().getDeclaredMethod("set" + myType,
                                                           String.class, int.class);

            int newAmount;

            if (isFixed) {
                newAmount = (int) get.invoke(unit, myName) + myAmount;
            }
            else {
                newAmount = Math
                        .round((float) ((int) get.invoke(unit, myName) + myAmount
                                                                         * effectiveness));
            }

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
     * Checks whether or not an outcome is legal
     * 
     * @param unit
     * @param effectiveness
     * @return true if legal, false otherwise
     */
    public boolean checkValidOutcome (GameUnit unit, double effectiveness) {
        
        if (isStat(unit, myName)) {
            myType = "Stat";
        }
        else {
            myType = "Item";
        }
        
        try {
            Method get = unit.getClass().getDeclaredMethod("get" + myType,
                                                           String.class);

            int oldAmount = (int) get.invoke(unit, myName);
            int newAmount;

            if (isFixed) {
                newAmount = (int) get.invoke(unit, myName) + myAmount;
            }
            else {
                newAmount = Math
                        .round((float) ((int) get.invoke(unit, myName) + myAmount
                                                                         * effectiveness));
            }

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
    
    private boolean isStat(GameUnit unit, String name) {
        return unit.getStats().getStatNames().contains(name);
    }

    public String getName () {
        return myName;
    }

    public void setName (String name) {
        myName = name;
    }

    public int getAmount () {
        return myAmount;
    }

    public void setAmount (int amount) {
        myAmount = amount;
    }

    @JsonProperty("fixed")
    public boolean isFixed () {
        return isFixed;
    }

    @JsonProperty("fixed")
    public void setIsFixed (boolean fixed) {
        isFixed = fixed;
    }
}
