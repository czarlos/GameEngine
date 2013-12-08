package dialog.renderers;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;


@SuppressWarnings("serial")
public class ComboStringRenderer extends DefaultTableCellRenderer {

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
            ((JLabel) cell).setText(value.toString());
        ((JLabel) cell).setHorizontalAlignment(JLabel.CENTER);

        if (isSelected)
            cell.setBackground(Color.LIGHT_GRAY);
        else cell.setBackground(null);
        return cell;
    }
}
