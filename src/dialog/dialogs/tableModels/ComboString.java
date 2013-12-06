package dialog.dialogs.tableModels;

/**
 * Provides a way to differentiate Strings used in JCombobox contexts from other strings for the
 * purposes of selecting appropriate cell editor/renderer
 * 
 * @author Leevi
 *
 */
public class ComboString {

    String myValue;

    public ComboString (String string) {
        myValue = string;
    }

    public String toString () {
        return myValue;
    }
}
