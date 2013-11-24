package dialog;


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

public class ImagePathEditor extends AbstractCellEditor
                         implements TableCellEditor,
			            ActionListener {
    File currentFile;
    JButton button;
    JFileChooser fileChooser;
    JDialog dialog;
    protected static final String EDIT = "edit";

    public ImagePathEditor() {
        

        button = new JButton();
        button.setActionCommand(EDIT);
        button.addActionListener(this);
        button.setBorderPainted(false);

        fileChooser = new JFileChooser();
        
    }

    /**
     * Handles events from the editor button and from
     * the dialog's OK button.
     */
    public void actionPerformed(ActionEvent e) {
        
            int returnVal = fileChooser.showDialog(button, "Choose");
            
            //actual file was chosen
            if(returnVal == 0) {            
                currentFile = fileChooser.getSelectedFile();

            }
            
            fireEditingStopped();

    }
    
    
    public Object getCellEditorValue() {
        return currentFile;
    }

    public Component getTableCellEditorComponent(JTable table,
                                                 Object value,
                                                 boolean isSelected,
                                                 int row,
                                                 int column) {
        currentFile = (File) value;
        return button;
    }
}

