package dialog.editors;

import javax.swing.JTable;
import dialog.dialogs.TableDialog;
import dialog.dialogs.tableModels.GameTableModel;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;


@SuppressWarnings("serial")
public class ModelEditor extends GameCellEditor {

    private GameTableModel myModel;
    private TableDialog statsEditor;
    private List<String> myEnumList;

    public ModelEditor (GameTableModel gtm) {
        this(gtm, new ArrayList<String>());
    }

    public ModelEditor (GameTableModel gtm, List<String> enumList) {
        myModel = gtm;
        myEnumList = enumList;
    }

    // opens and closes editor
    public void actionPerformed (ActionEvent e) {

        if (EDIT.equals(e.getActionCommand())) {
            statsEditor = new TableDialog(myModel, this, myEnumList);
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
