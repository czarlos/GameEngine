package view.player;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.Scrollable;
import javax.swing.SwingConstants;
import controller.editor.GridController;


/**
 * Panel to display tabs that are shown on the side of the player.
 */
@SuppressWarnings("serial")
public class SelectedInfoPanel extends JTabbedPane implements Scrollable {
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

            add("Actions", makeActionPane(actionInfo));
        }

        if (tileInfo != null && !tileInfo.isEmpty()) {
            add("Tile", new InfoPanel(tileInfo));
        }

        if (objectInfo != null && !objectInfo.isEmpty()) {
            add("Object", new InfoPanel(objectInfo));
        }

        repaint();
    }

    @Override
    public Dimension getPreferredScrollableViewportSize () {
        return getPreferredSize();
    }

    @Override
    public int getScrollableUnitIncrement (Rectangle visibleRect,
                                           int orientation, int direction) {
        return 10;
    }

    @Override
    public int getScrollableBlockIncrement (Rectangle visibleRect,
                                            int orientation, int direction) {
        return ((orientation == SwingConstants.VERTICAL) ? visibleRect.height
                                                        : visibleRect.width);
    }

    @Override
    public boolean getScrollableTracksViewportWidth () {
        return true;
    }

    @Override
    public boolean getScrollableTracksViewportHeight () {
        return false;
    }

    public JPanel makeActionPane (List<String> actionInfo) {
        return new ActionInfoPanel(actionInfo, myController);
    }
}