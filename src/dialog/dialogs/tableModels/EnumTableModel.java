package dialog.dialogs.tableModels;

import java.util.ArrayList;
import java.util.List;


/**
 * Takes an list of strings and an enum and displays those strings as editable
 * enums.
 * 
 * @author Leevi
 * 
 */
@SuppressWarnings("serial")
public class EnumTableModel extends GameTableModel {

    public EnumTableModel () {
        String[] names = { "" };
        myName = "List";
        setColumnNames(names);
    }

    @Override
    public Object[] getNew () {
        Object[] ret = new Object[myColumnNames.length];
        ret[0] = new ComboString("");
        return ret;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void loadObject (Object object) {
        myList.clear();
        List<String> list = (List<String>) object;
        for (String s : list) {
            Object[] array = new Object[myColumnNames.length];
            array[0] = new ComboString(s);
            addNewRow(array);
        }
    }

    @Override
    public Object getObject () {
        List<String> ret = new ArrayList<String>();
        for (Object[] row : myList) {
            String comboString = ((ComboString) row[0]).toString();
            ret.add(comboString);
        }
        return ret;
    }
}
