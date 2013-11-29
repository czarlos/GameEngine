package dialog.dialogs.tableModels;

import grid.GridConstants;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;


/**
 * Takes an list of strings and an enum and displays those strings as editable enums.
 * 
 * @author Leevi
 * 
 */
@SuppressWarnings("serial")
public class EnumTableModel extends GameTableModel {

    public EnumTableModel () {
        String[] names = { "List Items" };
        myName = "List";
        setColumnNames(names);
    }

    @Override
    public Object[] getNew () {
        Object[] ret = new Object[myColumnNames.length];
        ret[0] = new ComboString(GridConstants.DEFAULT_PASS_EVERYTHING);
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

    @SuppressWarnings("unchecked")
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
