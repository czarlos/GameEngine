package dialog;

import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import dialog.dialogs.tableModels.GameTableModel;

/**
 * 
 * @author Leevi, brooksmershon
 *
 */

@SuppressWarnings("serial")
public class GameJTable extends JTable {
    
    /**
     * GameTableModel passed in to control JTable
     * @param gtm
     */

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

