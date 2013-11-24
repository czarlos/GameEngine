package dialog;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

public class ListEditor extends AbstractCellEditor
implements TableCellEditor, ActionListener{

    /**
     * 
     */
    private static final long serialVersionUID = -5207662417684485180L;

    @Override
    public Object getCellEditorValue () {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void actionPerformed (ActionEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public Component getTableCellEditorComponent (JTable table,
                                                  Object value,
                                                  boolean isSelected,
                                                  int row,
                                                  int column) {
        // TODO Auto-generated method stub
        return null;
    }

}
