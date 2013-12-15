package dialog.renderers;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import dialog.Selector;


/**
 * Displays a Selector object as the current toString value of the selected item
 * 
 * @author Leevi
 * 
 */
@SuppressWarnings("serial")
public class SelectorRenderer extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent (JTable table,
                                                    Object value,
                                                    boolean isSelected,
                                                    boolean hasFocus,
                                                    int row,
                                                    int column) {
        Component cell = super.getTableCellRendererComponent(table, value,
                                                             isSelected, hasFocus, row, column);

        if (value != null)
            ((JLabel) cell).setText(((Selector) value).getValue().toString());
        ((JLabel) cell).setHorizontalAlignment(JLabel.CENTER);

        if (isSelected)
            cell.setBackground(Color.LIGHT_GRAY);
        else cell.setBackground(null);
        return cell;
    }
}
