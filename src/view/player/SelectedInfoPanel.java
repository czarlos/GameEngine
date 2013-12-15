package view.player;

import java.awt.Dimension;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.ScrollPaneLayout;
import controller.player.GridController;


/**
 * Panel to display tabs that are shown on the side of the player.
 * These tabs contain information about the currently selected tile,
 * object, and actions available to that object.
 */

@SuppressWarnings("serial")
public class SelectedInfoPanel extends JTabbedPane {
    protected GridController myController;

    /**
     * Create new SelectedInfoPanel with GridController actions will be executed by.
     * 
     * @param controller GridController action buttons will call actions through
     */
    public SelectedInfoPanel (GridController controller) {
        myController = controller;
        setPreferredSize(new Dimension(300, 500));
    }

    /**
     * Create a new SelectedInfoPanel without a controller
     * (Action buttons won't work)
     */
    public SelectedInfoPanel () {
        setPreferredSize(new Dimension(200, 200));
    }

    /**
     * Create tabs for SelectedInfoPane. Called to update a SelectedInfoPane
     * when new information needs to be displayed.
     * 
     * @param actionInfo Actions to be displayed
     * @param tileInfo Info on a tile to be displayed
     * @param objectInfo Info on an object to be displayed
     */
    public void makeTabs (List<String> actionInfo, List<String> tileInfo,
                          List<String> objectInfo) {

        this.removeAll();

        if (actionInfo != null && !actionInfo.isEmpty()) {
            JScrollPane pane = new JScrollPane(makeActionPane(actionInfo));
            pane.setLayout(new ScrollPaneLayout());
            add("Actions", pane);
        }

        if (objectInfo != null && !objectInfo.isEmpty()) {
            JScrollPane pane = new JScrollPane(new InfoPanel(objectInfo));
            pane.setLayout(new ScrollPaneLayout());
            add("Object", pane);
        }

        if (tileInfo != null && !tileInfo.isEmpty()) {
            JScrollPane pane = new JScrollPane(new InfoPanel(tileInfo));
            pane.setLayout(new ScrollPaneLayout());
            add("Tile", pane);
        }

        repaint();
    }

    /**
     * Create action pane. Can be overridden to change how actions are
     * displayed in and InfoPanel (Strings vs buttons)
     * 
     * @param actionInfo List of actions
     * @return JPanel representing actions
     */
    protected JPanel makeActionPane (List<String> actionInfo) {
        return new ActionInfoPanel(actionInfo, myController);
    }
}
