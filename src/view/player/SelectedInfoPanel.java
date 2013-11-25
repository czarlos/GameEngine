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
    protected JTabbedPane myTabs;
    protected GridController myController;

    public SelectedInfoPanel (GridController controller) {
        myTabs = new JTabbedPane();
        myController = controller;

    }
    @SuppressWarnings("unchecked")
    public void makeTabs (List<String> buttonTab,
                          List<String> tileInfo,
                          List<String> objectInfo) {
        if (tileInfo != null) {
            JScrollPane pane = new JScrollPane(new InfoPanel(tileInfo));
            myTabs.add("Tile", pane);
        }
        if(objectInfo!=null){
            JScrollPane pane=new JScrollPane(new InfoPanel(objectInfo));
            myTabs.add("Objects",pane);
        }
        
        if (buttonTab != null) {
            JScrollPane pane =
                    new JScrollPane(new ActionInfoPanel(buttonTab, myController));
            myTabs.add("Actions",pane);
        }

        add(myTabs);
        repaint();
    }
}
