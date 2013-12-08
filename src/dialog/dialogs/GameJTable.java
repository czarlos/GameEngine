package dialog.dialogs;

import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import dialog.dialogs.tableModels.GameTableModel;


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
