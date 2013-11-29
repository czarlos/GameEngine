package dialog.renderers;

import grid.GridConstants;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;


/**
 * Defines how to render a stat object
 * @author Leevi
 * @author brooksmershon
 */
@SuppressWarnings("serial")
public class StatsRenderer extends DefaultTableCellRenderer {

    public Component getTableCellRendererComponent (JTable table, Object value, boolean isSelected,
                                                    boolean hasFocus, int row, int column) {
        Component cell =
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
                                                    column);

        ((JLabel) cell).setText(GridConstants.STATS);
        ((JLabel) cell).setHorizontalAlignment(JLabel.CENTER);

        if (isSelected) {
            cell.setBackground(Color.GREEN);
        }
        else {
            cell.setBackground(null);
        }
        return cell;
    }
}
