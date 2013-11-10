package dialog;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

/**
 * @author brooksmershon
 *
 * Allows for an image thumbnail to be rendered as a cell in a JTable 
 */
public class ThumbnailRenderer extends DefaultTableCellRenderer {

    /**
     * 
     */
    private static final long serialVersionUID = -4561316688532828835L;

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                   boolean hasFocus, int row, int column) {
        Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
                                                             column);
        ((JLabel)cell).setIcon((Icon)value);
        ((JLabel)cell).setText("");
        ((JLabel)cell).setHorizontalAlignment(JLabel.CENTER);
        if (isSelected) {
            cell.setBackground(Color.blue);
        } else {
            cell.setBackground(null);
        }
        return cell;
    }
}
