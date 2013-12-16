package view.canvas;

import grid.Coordinate;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collection;
import javax.swing.BorderFactory;
import controllers.Manager;

/**
 * Implementation of Canvas that draws a grid. A Drawable grid is gotten
 * from the Manager. The manager is also used to convert pixel coordinates for clicks on 
 * GridCanvas to Grid Coordinates without the GridCanvas having to know about the grid represents.
 * 
 * GridCanvas also implements the a subscriber pattern to notify other elements of when the grid 
 * has been clicked. 
 *
 */
public class GridCanvas extends Canvas {

    /**
     * Default dimensions to draw tiles at. No matter how large a grid is,
     * it's tiles will have consistent size with the scrolling pane growing
     * to accommodate more tiles.
     */
    private final int DEFAULT_TILE_DISPLAY_SIZE = 50;
    Manager myManager;
    Collection<GridMouseListener> myClickSubscribers;

    private static final long serialVersionUID = -3908147776463294489L;

    /**
     * Create a new grid canvas
     * @param m Manager to get Drawable grid from and convert coordinates.
     */
    public GridCanvas (Manager m) {
        setBorder(BorderFactory.createLineBorder(Color.black));
        myManager = m;
        myClickSubscribers = new ArrayList<>();
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked (MouseEvent e) {
                notifySubscribersOfClick(e);
            }
        });
    }

    protected void notifySubscribersOfClick (MouseEvent e) {

        Coordinate clickGridCoordinate =
                myManager.getCoordinate(
                                        (double) e.getX() / getSize().width, (double) e.getY()
                                                                             / getSize().height);

        for (GridMouseListener subscriber : myClickSubscribers) {
            subscriber.gridClicked(clickGridCoordinate);
        }
        repaint();
    }

    /**
     * Add a GridMouseListener to be notified when the grid is clicked.
     * @param l Instance of GridMouseNotifier to be notified when the grid is clicked
     */
    public void addGridMouseListener (GridMouseListener l) {
        myClickSubscribers.add(l);
    }

    @Override
    public void paintComponent (Graphics g) {
        super.paintComponent(g);
        int height = getSize().height;
        int width = getSize().width;
        myManager.getGrid().draw(g, STARTING_X, STARTING_Y, width, height);
    }

    @Override
    public Dimension getPreferredSize () {
        return myManager.calculateGridDimensions(DEFAULT_TILE_DISPLAY_SIZE);
    }

    @Override
    public int getScrollableUnitIncrement (Rectangle visibleRect,
                                           int orientation, int direction) {
        return DEFAULT_TILE_DISPLAY_SIZE;
    }

    @Override
    public int getScrollableBlockIncrement (Rectangle visibleRect,
                                            int orientation, int direction) {
        return DEFAULT_TILE_DISPLAY_SIZE;
    }

}
