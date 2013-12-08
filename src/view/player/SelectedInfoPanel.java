package view.player;

import java.awt.Dimension;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.ScrollPaneLayout;
import controller.editor.GridController;


/**
 * Panel to display tabs that are shown on the side of the player.
 */
@SuppressWarnings("serial")
public class SelectedInfoPanel extends JTabbedPane {
    protected GridController myController;

    public SelectedInfoPanel (GridController controller) {
        myController = controller;
        setPreferredSize(new Dimension(300, 500));

    }

    public SelectedInfoPanel () {
        setPreferredSize(new Dimension(200, 200));
    }

    public void makeTabs (List<String> actionInfo, List<String> tileInfo,
                          List<String> objectInfo) {

        if (actionInfo != null && !actionInfo.isEmpty()) {
            JScrollPane pane = new JScrollPane(makeActionPane(actionInfo));
            pane.setLayout(new ScrollPaneLayout());
            add("Actions", pane);
        }

        if (tileInfo != null && !tileInfo.isEmpty()) {
            JScrollPane pane = new JScrollPane(new InfoPanel(tileInfo));
            pane.setLayout(new ScrollPaneLayout());
            add("Tile", pane);
        }

        if (objectInfo != null && !objectInfo.isEmpty()) {
            JScrollPane pane = new JScrollPane(new InfoPanel(objectInfo));
            pane.setLayout(new ScrollPaneLayout());
            add("Object", pane);
        }

        repaint();
    }

    public JPanel makeActionPane (List<String> actionInfo) {
        return new ActionInfoPanel(actionInfo, myController);
    }
}
