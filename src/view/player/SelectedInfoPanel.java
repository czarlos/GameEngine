package view.player;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.List;
import grid.Coordinate;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.Scrollable;
import javax.swing.SwingConstants;
import controller.editor.GridController;


/**
 * Panel to display tabs that are shown on the side of
 * the player.
 */
public class SelectedInfoPanel extends JPanel implements Scrollable {
    protected JTabbedPane myTabs;
    protected GridController myController;

    public SelectedInfoPanel (GridController controller) {
        myTabs = new JTabbedPane();
        myController = controller;
        setPreferredSize(new Dimension(300,500));

    }

    @SuppressWarnings("unchecked")
    public void makeTabs (List<String> buttonTab,
                          List<String> tileInfo,
                          List<String> objectInfo) {
        if (tileInfo != null) {
            myTabs.add("Tile", new InfoPanel(tileInfo));
        }

        if (objectInfo != null) {
            myTabs.add("Objects", new InfoPanel(objectInfo));
        }

        if (buttonTab != null) {

            myTabs.add("Actions", new ActionInfoPanel(buttonTab, myController));
            ;
        }

        add(myTabs);
        repaint();
    }

    @Override
    public Dimension getPreferredScrollableViewportSize () {
        return getPreferredSize();
    }

    @Override
    public int getScrollableUnitIncrement (Rectangle visibleRect, int orientation, int direction) {
        return 10;
    }

    @Override
    public int getScrollableBlockIncrement (Rectangle visibleRect, int orientation, int direction) {
        return ((orientation == SwingConstants.VERTICAL) ? visibleRect.height : visibleRect.width);
    }

    @Override
    public boolean getScrollableTracksViewportWidth () {
        return true;
    }

    @Override
    public boolean getScrollableTracksViewportHeight () {
        return false;
    }
}
