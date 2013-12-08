package dialog.editors;

import javax.swing.JTable;
import controllers.WorldManager;
import dialog.dialogs.TableDialog;
import dialog.dialogs.tableModels.GameTableModel;
import java.awt.Component;
import java.awt.event.ActionEvent;


@SuppressWarnings("serial")
public class ModelEditor extends GameCellEditor {

    private GameTableModel myModel;
    private TableDialog editorDialog;
    private WorldManager myWM;

    public ModelEditor (GameTableModel gtm) {
        this(gtm, new WorldManager());
    }

    public ModelEditor (GameTableModel gtm, WorldManager wm) {
        myModel = gtm;
        myWM = wm;
    }

    // opens and closes editor
    public void actionPerformed (ActionEvent e) {

        if (EDIT.equals(e.getActionCommand())) {
            editorDialog = new TableDialog(myModel, this, myWM);
            editorDialog.setVisible(true);
        }
        else {
            current = myModel.getObject();
            editorDialog.stopEditing();
            editorDialog.setVisible(false);
            //stopCellEditing();
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
