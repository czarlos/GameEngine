package dialog.editors;

import javax.swing.JTable;
import dialog.dialogs.TableDialog;
import dialog.dialogs.tableModels.GameTableModel;
import java.awt.Component;
import java.awt.event.ActionEvent;


@SuppressWarnings("serial")
public class ModelEditor extends GameCellEditor {

    GameTableModel myModel;
    TableDialog statsEditor;

    public ModelEditor (GameTableModel stm) {
        myModel = stm;
    }

    // opens and closes editor
    public void actionPerformed (ActionEvent e) {

        if (EDIT.equals(e.getActionCommand())) {
            statsEditor = new TableDialog(myModel, this);
            statsEditor.setVisible(true);
        }
        else {
            current = myModel.getObject();
            statsEditor.setVisible(false);
            fireEditingStopped();
        }
    }

    public Component getTableCellEditorComponent (JTable table,
                                                  Object value,
                                                  boolean isSelected,
                                                  int row,
                                                  int column) {
        current = value;
        myModel.loadObject(value);
        return button;
    }
}
