package dialog.dialogs.tableModels;

import java.util.ArrayList;
import java.util.List;
import controllers.EditorData;
import dialog.Selector;


/**
 * Takes an list of strings and an enum and displays those strings as editable
 * enums.
 * 
 * @author Leevi
 * 
 */
@SuppressWarnings("serial")
public class EnumTableModel extends GameTableModel {

    private List<String> myComboList;
    
    public EnumTableModel (List<String> list, EditorData ED) {
        String[] names = { "" };
        myName = "List Item";
        setColumnNames(names);
        myComboList = list;
        myED = ED;
    }

    @Override
    public Object[] getNew () {
        Object[] ret = new Object[myColumnNames.length];
        ret[0] = new Selector(myComboList);
        return ret;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void loadObject (Object object) {
        myList.clear();
        List<String> list = (List<String>) object;
        for (String s : list) {
            Object[] array = new Object[myColumnNames.length];
            array[0] = new Selector(myComboList, s);
            addNewRow(array);
        }
    }

    @Override
    public Object getObject () {
        List<String> ret = new ArrayList<String>();
        for (Object[] row : myList) {
            String comboString = (String) ((Selector) row[0]).getValue();
            ret.add(comboString);
        }
        return ret;
    }
}
