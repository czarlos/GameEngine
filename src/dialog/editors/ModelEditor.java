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
    private TableDialog statsEditor;
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
            statsEditor = new TableDialog(myModel, this, myWM);
            statsEditor.setVisible(true);
        }
        else {
            current = myModel.getObject();
            statsEditor.setVisible(false);
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
