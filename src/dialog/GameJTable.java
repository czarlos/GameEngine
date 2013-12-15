package dialog;

import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import dialog.dialogs.tableModels.GameTableModel;


/**
 * A JTable that checks for a specific cell editor for each cell, and doesn't assume
 * the same editor for an entire column
 * 
 * @author Leevi
 * 
 */

@SuppressWarnings("serial")
public class GameJTable extends JTable {

    public GameJTable (GameTableModel gtm) {
        super(gtm);
    }

    @Override
    public TableCellEditor getCellEditor (int row, int column) {
        Object value = getValueAt(row, column);
        if (value != null) { return getDefaultEditor(value.getClass()); }
        return super.getCellEditor(row, column);
    }
}
