package dialog.editors;

import gameObject.Stats;
import javax.swing.JTable;
import dialog.dialogs.TableDialog;
import dialog.dialogs.tableModels.StatsTableModel;
import java.awt.Component;
import java.awt.event.ActionEvent;


@SuppressWarnings("serial")
public class StatsEditor extends GameCellEditor {

    StatsTableModel model;
    TableDialog statsEditor;

    public StatsEditor () {
        model = new StatsTableModel();
    }

    // opens and closes editor
    public void actionPerformed (ActionEvent e) {

        if (EDIT.equals(e.getActionCommand())) {
            statsEditor = new TableDialog(model, this);
            statsEditor.setVisible(true);
        }
        else {
            current = model.getStats();
            statsEditor.setVisible(false);
            fireEditingStopped();
        }
    }

    public Component getTableCellEditorComponent (JTable table,
                                                  Object value,
                                                  boolean isSelected,
                                                  int row,
                                                  int column) {
        current = (Stats) value;
        model.loadStats(value);
        return button;
    }
}
