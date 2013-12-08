package editor;

import java.util.List;


/**
 * Provides a way to differentiate Strings used in JCombobox contexts from other strings for the
 * purposes of selecting appropriate cell editor/renderer
 * 
 * @author Leevi
 * 
 */
public class Selector {

    private Object myValue;
    private List<?> myValues;

    public Selector (List<?> values) {
        this(values, values.get(0));
    }

    public Selector (List<?> values, Object value) {
        myValues = values;
        myValue = value;
    }

    public List<?> getValues () {
        return myValues;
    }

    public Object getValue () {
        return myValue;
    }

    @Override
    public String toString () {
        return myValue.toString();
    }
}
