package dialog.editors;

import javax.swing.JTable;
import dialog.dialogs.TableDialog;
import dialog.dialogs.tableModels.GameTableModel;
import java.awt.Component;
import java.awt.event.ActionEvent;


/**
 * Allows you to edit table models within table models, opens up a new EditorDialog
 * with the appropriate table rendered.
 * 
 * @author Leevi
 * 
 */
@SuppressWarnings("serial")
public class ModelEditor extends GameCellEditor {

    private GameTableModel myModel;
    private TableDialog editorDialog;

    public ModelEditor (GameTableModel gtm) {
        myModel = gtm;
    }

    // opens and closes editor
    public void actionPerformed (ActionEvent e) {

        if (EDIT.equals(e.getActionCommand())) {
            editorDialog = new TableDialog(myModel, this);
            editorDialog.setVisible(true);
        }
        else {
            current = myModel.getObject();
            editorDialog.stopEditing();
            editorDialog.setVisible(false);
            fireEditingStopped();
        }
    }

    public Component getTableCellEditorComponent (JTable table, Object value,
                                                  boolean isSelected, int row, int column) {
        current = value;
        myModel.loadObject(value);
        return button;
    }
}
