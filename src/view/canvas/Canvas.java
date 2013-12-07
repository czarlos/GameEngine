package view.canvas;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import javax.swing.JPanel;
import javax.swing.Scrollable;


public class Canvas extends JPanel implements Scrollable {

    /**
     * 
     */
    private static final long serialVersionUID = 5035575821683474890L;

    protected static final int STARTING_X = 0;
    protected static final int STARTING_Y = 0;

    public Canvas () {
        super();
    }

    public Canvas (LayoutManager arg0) {
        super(arg0);
    }

    public Canvas (boolean arg0) {
        super(arg0);
    }

    public Canvas (LayoutManager arg0, boolean arg1) {
        super(arg0, arg1);
    }

    public Dimension getPreferredSize () {
        return new Dimension(1000, 1000);
    }

    /**
     * Method to define what gets painted to canvas.
     */
    public void paintComponent (Graphics g) {
        super.paintComponent(g);
    }

    public int getHeight () {
        return getSize().height;
    }

    public int getWidth () {
        return getSize().width;
    }

    public Dimension getPreferredScrollableSize () {
        return new Dimension(500, 500);
    }

    public Dimension getPreferredScrollableViewportSize () {
        return getPreferredScrollableSize();
    }

    public int getScrollableUnitIncrement (Rectangle visibleRect,
                                           int orientation, int direction) {
        return 1;
    }

    public int getScrollableBlockIncrement (Rectangle visibleRect,
                                            int orientation, int direction) {
        return 1;
    }

    public boolean getScrollableTracksViewportWidth () {
        return false;
    }

    public boolean getScrollableTracksViewportHeight () {
        return false;
    }

}
