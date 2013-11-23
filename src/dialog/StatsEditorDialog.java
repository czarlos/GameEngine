package dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;


/**
 * @author brooksmershon
 *
 * Presents a view of current units with the ability to add, remove operations for a scrolling
 * table view of row definitions for units
 * 
 * 
 */
public class StatsEditorDialog extends JDialog{
    


    /**
     * 
     */
    private static final long serialVersionUID = 370838418473171385L;
    private final int DEFAULT_WIDTH = 300;
    private final int DEFAULT_HEIGHT = 500;
    
    /**
     * 
     * @param model - a TableModel class which provides getter and setter methods
     * for cell rendering and editing
     */
    public StatsEditorDialog(GameTableModel model) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        
        JButton ok = new JButton("OK");
        buttonPanel.add(ok);
        

        JTable table = new JTable(model);
        
        table.setPreferredScrollableViewportSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
        table.setFillsViewportHeight(true);
        
        JScrollPane scrollPane = new JScrollPane(table);
        
        
       panel.add(scrollPane);
       add(panel);
       add(buttonPanel);
       setSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
    }

}
