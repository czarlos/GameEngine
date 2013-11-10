package dialog;

import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;


public class UnitEditorPanel extends JPanel{
    


    /**
     * 
     */
    private static final long serialVersionUID = 370838418473171385L;

    public UnitEditorPanel(UnitTableModel model) {
        super(new GridLayout(1,0));

        JTable table = new JTable(model);
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);
        
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);
    }

}
