package dialog.editors;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.AbstractCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import dialog.Selector;


/**
 * Allows users to edit the value of a Selector class by providing a ComboBox of Selectors
 * (which to users appear as strings)
 * 
 * @author Leevi
 * 
 */
@SuppressWarnings("serial")
public class ComboBoxEditor extends AbstractCellEditor implements TableCellEditor {

    JComboBox<Selector> myComboBox;

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
        JComboBox<Selector> comboBox = new JComboBox<Selector>();
        List<?> list = ((Selector) value).getValues();
        for (Object o : list) {
            Selector newCS = new Selector(((Selector) value).getValues(), o);
            comboBox.addItem(newCS);
        }

        myComboBox = comboBox;
        myComboBox.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent event) {
                stopCellEditing();
                fireEditingStopped();
            }
        });

        return myComboBox;
    }

}
