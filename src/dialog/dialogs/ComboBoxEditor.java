package dialog.dialogs;

import java.awt.Component;
import javax.swing.AbstractCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import dialog.dialogs.tableModels.ComboString;


@SuppressWarnings("serial")
public class ComboBoxEditor extends AbstractCellEditor implements TableCellEditor {

    JComboBox<ComboString> myComboBox;

    public ComboBoxEditor () {
    }

    @Override
    public Object getCellEditorValue () {
        return myComboBox.getSelectedItem();
    }

    @Override
    public Component getTableCellEditorComponent (JTable table,
                                                  Object value,
                                                  boolean isSelected,
                                                  int row,
                                                  int column) {
        JComboBox<ComboString> comboBox = new JComboBox<ComboString>();
        for (String s : ((ComboString) value).getValues()) {
            ComboString newCS = new ComboString(((ComboString) value).getValues(), s);
            comboBox.addItem(newCS);
        }
        myComboBox = comboBox;

        return myComboBox;
    }

}
