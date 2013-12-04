package dialog;

import gameObject.Stats;
import grid.Tile;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import view.Customizable;


/**
 * @author brooksmershon
 * 
 *         Presents a view of current units with the ability to add, remove operations for a
 *         scrolling
 *         table view of row definitions for units
 * 
 * 
 */
public class TileEditorDialog extends TableDialog {

    /**
     * 
     */
    private static final long serialVersionUID = 370838418473171385L;

    private final int DEFAULT_WIDTH = 52;
    private final int DEFAULT_HEIGHT = 500;

    /**
     * 
     * @param model - a TableModel class which provides getter and setter methods
     *        for cell rendering and editing
     */
    public TileEditorDialog (GameTableModel model, ActionListener okListener) {

        super(model, okListener);

        

    }

    

}