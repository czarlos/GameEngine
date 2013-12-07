package dialog.editors;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

/**
 * 
 * @author Leevi, brooksmershon
 *
 */

@SuppressWarnings("serial")
public abstract class GameCellEditor extends AbstractCellEditor implements
        TableCellEditor, ActionListener {

    protected static final String EDIT = "edit";
    JButton button;
    Object current;

    public GameCellEditor () {
        button = new JButton();
        button.setActionCommand(EDIT);
        button.addActionListener(this);
        button.setBorderPainted(false);
    }

    @Override
    public Object getCellEditorValue () {
        return current;
    }

    @Override
    public abstract void actionPerformed (ActionEvent e);

    @Override
    public Component getTableCellEditorComponent (JTable table, Object value,
                                                  boolean isSelected, int row, int column) {
        current = value;
        return button;
    }
}
