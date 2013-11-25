package dialog;


import gameObject.Stats;
import javax.swing.AbstractCellEditor;
import javax.swing.table.TableCellEditor;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JTable;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class StatsEditor extends AbstractCellEditor
                         implements TableCellEditor,
                                    ActionListener {
    Stats currentStats;
    JButton button;
    StatsEditorDialog statsEditor;
    protected static final String EDIT = "edit";

    public StatsEditor() {
        

        button = new JButton();
        button.setActionCommand(EDIT);
        button.addActionListener(this);
        button.setBorderPainted(false);
        
        
    }


    public void actionPerformed(ActionEvent e) {

            StatsTableModel model = new StatsTableModel();
            
            model.loadStats(currentStats);
            
            statsEditor = new StatsEditorDialog(model);

            fireEditingStopped();

    }
    
    
    public Object getCellEditorValue() {
        return currentStats;
    }

    public Component getTableCellEditorComponent(JTable table,
                                                 Object value,
                                                 boolean isSelected,
                                                 int row,
                                                 int column) {
        currentStats = (Stats) value;
        return button;
    }
}

