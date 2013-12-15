package view.canvas;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import javax.swing.JPanel;
import javax.swing.Scrollable;

/**
 * A Canvas is a JPanel that is scrollable and exposes paintComponent 
 * functionality so the pain can be painted to directly using a Graphics object.
 *
 */
public class Canvas extends JPanel implements Scrollable {

    private static final long serialVersionUID = 5035575821683474890L;

    /**
     * X Position to start drawing from.
     */
    protected static final int STARTING_X = 0;
    
    /**
     * Y Position to start drawing from.
     */
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

    @Override
    public int getHeight () {
        return getSize().height;
    }

    @Override
    public int getWidth () {
        return getSize().width;
    }
    
    @Override
    public Dimension getPreferredScrollableViewportSize () {
        return new Dimension(500, 500);
    }

    @Override
    public int getScrollableUnitIncrement (Rectangle visibleRect,
                                           int orientation, int direction) {
        return 1;
    }

    @Override
    public int getScrollableBlockIncrement (Rectangle visibleRect,
                                            int orientation, int direction) {
        return 1;
    }

    @Override
    public boolean getScrollableTracksViewportWidth () {
        return false;
    }

    @Override
    public boolean getScrollableTracksViewportHeight () {
        return false;
    }

}
