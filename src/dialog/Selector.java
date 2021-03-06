package dialog;

import java.util.List;


/**
 * Provides a way to differentiate Strings used in JComboBox contexts from other strings for the
 * purposes of selecting appropriate cell editor/renderer
 * 
 * @author Leevi
 * 
 */
public class Selector {

    private Object myValue;
    private List<?> myValues;

    /**
     * Construct Selector with default selected value set to the first object in values
     * 
     * @param values
     */
    public Selector (List<?> values) {
        this(values, values.get(0));
    }

    /**
     * 
     * @param values Selector options
     * @param value Current value of selector
     */
    public Selector (List<?> values, Object value) {
        myValues = values;
        myValue = value;
    }

    /**
     * Returns all possible values for the selector
     * 
     * @return List<?> values
     */
    public List<?> getValues () {
        return myValues;
    }

    /**
     * Get currently selected value
     * 
     * @return Object
     */
    public Object getValue () {
        return myValue;
    }

    @Override
    public String toString () {
        return myValue.toString();
    }
}
