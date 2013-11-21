package view.canvas;

import grid.Coordinate;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collection;
import javax.swing.BorderFactory;
import controllers.WorldManager;


public class GridCanvas extends Canvas {

    WorldManager myWM;
    Collection<GridMouseListener> myClickSubscribers;

    private static final long serialVersionUID = -3908147776463294489L;

    public GridCanvas (WorldManager wm) {
        setBorder(BorderFactory.createLineBorder(Color.black));
        myWM = wm;
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
                myWM.getCoordinate((double) e.getX() / getSize().width, (double) e.getY() /
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
        myWM.getGrid().draw(g, STARTING_X, STARTING_Y, width, height);
    }

}
