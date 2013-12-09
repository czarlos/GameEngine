package view.player;

import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.Scrollable;
import javax.swing.SwingConstants;


/**
 * A scrollable pane for game info panels to extend
 */

@SuppressWarnings("serial")
public abstract class ScrollableListPane extends JPanel implements Scrollable {

    protected final int DATA_HEIGHT = 30;
    protected final int WIDTH = 300;

    public ScrollableListPane () {
        super();
    }

    public ScrollableListPane (List<String> data) {
        populate(data);
    }

    public ScrollableListPane (LayoutManager layout) {
        super(layout);
    }

    public abstract void populate (List<String> data);

    public ScrollableListPane (boolean isDoubleBuffered) {
        super(isDoubleBuffered);
    }

    public ScrollableListPane (LayoutManager layout, boolean isDoubleBuffered) {
        super(layout, isDoubleBuffered);
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
}
