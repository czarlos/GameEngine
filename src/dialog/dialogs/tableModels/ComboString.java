package dialog.dialogs.tableModels;

import java.util.ArrayList;
import java.util.List;


/**
 * Provides a way to differentiate Strings used in JCombobox contexts from other strings for the
 * purposes of selecting appropriate cell editor/renderer
 * 
 * @author Leevi
 * 
 */
public class ComboString {

    String myValue;
    List<String> myValues = new ArrayList<String>();

    public ComboString (List<String> values) {
        this(values, values.get(0));
    }

    public ComboString (List<String> values, String value) {
        myValue = value;
        myValues = values;
    }

    public void setValues (List<String> values) {
        myValues = values;
    }

    public List<String> getValues () {
        return myValues;
    }

    public String toString () {
        return myValue;
    }
}
