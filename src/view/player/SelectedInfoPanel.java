package view.player;

import java.util.List;
import grid.Coordinate;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import controller.editor.GridController;


public class SelectedInfoPanel extends JPanel {
    protected Coordinate myCoordinate;
    protected JTabbedPane myTabs;
    protected GridController myController;

    public SelectedInfoPanel (GridController controller) {
        myTabs = new JTabbedPane();
        myController = controller;
    }

    public void makeTabs (List<String> info, List<String> actions) {
        if (info != null) {
            myTabs.add("Tile Info", new InfoPanel(info));
        }

        if (actions != null) {
            myTabs.add("Actions", new ActionInfoPanel(actions, myController, myCoordinate));
        }
    }
}
