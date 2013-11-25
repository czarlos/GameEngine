package view.player;

import java.util.List;
import grid.Coordinate;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import controller.editor.GridController;

/**
 * Panel to display tabs that are shown on the side of 
 * the player.
 */
public class SelectedInfoPanel extends JPanel {
    protected Coordinate myCoordinate;
    protected JTabbedPane myTabs;
    protected GridController myController;

    public SelectedInfoPanel (GridController controller) {
        myTabs = new JTabbedPane();
        myController = controller;
    }

    @SuppressWarnings("unchecked")
    public void makeTabs (List<String> buttonTab, List<String> ... tabs) {
        for (List<String> list : tabs) {
            if (list != null) {
                JScrollPane pane = new JScrollPane(new InfoPanel(list));
                myTabs.add(pane);
            }
        }

        if (buttonTab != null) {
            JScrollPane pane =
                    new JScrollPane(new ActionInfoPanel(buttonTab, myController, myCoordinate));
            myTabs.add(pane);
        }
    }
}
