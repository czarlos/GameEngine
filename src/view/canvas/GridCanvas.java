package view.canvas;

import grid.Coordinate;
import grid.Tile;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collection;
import javax.swing.BorderFactory;
import controllers.Manager;


public class GridCanvas extends Canvas {

    Manager myManager;
    Collection<GridMouseListener> myClickSubscribers;
    Tile t;

    private static final long serialVersionUID = -3908147776463294489L;

    public GridCanvas (Manager wm) {
        setBorder(BorderFactory.createLineBorder(Color.black));
        myManager = wm;
        myClickSubscribers = new ArrayList<>();
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked (MouseEvent e) {
                notifySubscribersOfClick(e);
            }
        });
    }

    public void notifySubscribersOfClick (MouseEvent e) {

        Coordinate clickGridCoordinate =
                myManager.getCoordinate((double) e.getX() / getSize().width, (double) e.getY() /
                                                                             getSize().height);

        for (GridMouseListener subscriber : myClickSubscribers) {
            subscriber.gridClicked(clickGridCoordinate);
        }
        repaint();
    }

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

    public int getHeight () {
        return getSize().height;
    }

    public int getWidth () {
        return getSize().width;
    }

}
