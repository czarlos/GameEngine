package dialog;

import gameObject.Stats;
import grid.Tile;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import view.Customizable;
import com.sun.media.sound.ModelAbstractChannelMixer;


/**
 * @author brooksmershon
 *
 * Presents a view of current units with the ability to add, remove operations for a scrolling
 * table view of row definitions for units
 * 
 * 
 */
public class TileEditorDialog extends TableDialog{
    

    /**
     * 
     */
    private static final long serialVersionUID = 370838418473171385L;
    
    private final int DEFAULT_WIDTH = 52;
    private final int DEFAULT_HEIGHT = 500;
    
    
    /**
     * 
     * @param model - a TableModel class which provides getter and setter methods
     * for cell rendering and editing
     */
    public TileEditorDialog(GameTableModel model) {
        
        super(model);
        
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        
        JButton addNewButton = new JButton("add new");
        
        addNewButton.addActionListener(new AddNewListener(model));
        
        buttonPanel.add(addNewButton);
        
        add(buttonPanel, BorderLayout.SOUTH);
        
        pack();
        

    }
    
    private class AddNewListener implements ActionListener {

        private JDialog dialog;
        private ImageCreator imageCreator;
        private GameTableModel myModel;

        public AddNewListener (GameTableModel model) {
            super();
            myModel = model;
        }

        public void actionPerformed (ActionEvent e) {
            ArrayList<Customizable> newList = new ArrayList<Customizable>(); 
            Tile defTile = new Tile();
            defTile.setName("New Tile");
            defTile.setImageAndPath("resources/grass.png");
            defTile.setMoveCost(1);
            
            Stats s = new Stats();
            s.setStats(new HashMap<String, Integer>());
            
            defTile.setStats(s);
                
            newList.add(defTile);
            myModel.addPreviouslyDefined(newList);
        }
    }
    
    private class okListener implements ActionListener {

        private JDialog dialog;
        private ImageCreator imageCreator;
        private GameTableModel myModel;

        public okListener (GameTableModel model) {
            super();
            myModel = model;
        }

        public void actionPerformed (ActionEvent e) {
            //TODO:
                //hand ok click
        }
    }

}
