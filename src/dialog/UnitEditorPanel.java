package dialog;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;


/**
 * @author brooksmershon
 *
 * Presents a view of current units with the ability to add, remove operationss
 * 
 * 
 */
public class UnitEditorPanel extends JPanel{
    


    /**
     * 
     */
    private static final long serialVersionUID = 370838418473171385L;

    public UnitEditorPanel(UnitTableModel model) {
        super(new GridLayout(1,0));

        JTable table = new JTable(model);
        table.setDefaultRenderer(ImageIcon.class,
                                 new ThumbnailRenderer());
        table.setDefaultEditor(ImageIcon.class,
                               new ImageEditor());
        table.setRowHeight(52);
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);
        
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);
    }

}
